package com.example.diceless.domain.match.reducer

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.core.utils.prepareCommanderDamage
import com.example.diceless.domain.model.LifeChangeEvent
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryChangeSource
import com.example.diceless.domain.model.MatchHistoryRegistry
import com.example.diceless.domain.model.PendingLifeChange
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.getDefaultCounterData
import com.example.diceless.domain.usecase.EndCurrentOpenMatchUseCase
import com.example.diceless.domain.usecase.GetAllPlayersUseCase
import com.example.diceless.domain.usecase.GetCurrentOpenMatchUseCase
import com.example.diceless.domain.usecase.InsertPlayerWithBackgroundUseCase
import com.example.diceless.domain.usecase.RegisterMatchHistoryUseCase
import com.example.diceless.domain.usecase.RegisterMatchUseCase
import com.example.diceless.domain.usecase.UpdateMatchUseCase
import com.example.diceless.domain.usecase.UpdatePlayersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchMiddleware @Inject constructor(
    private val insertPlayerWithBackgroundUseCase: InsertPlayerWithBackgroundUseCase,
    private val updatePlayersUseCase: UpdatePlayersUseCase,
    private val registerMatchHistoryUseCase: RegisterMatchHistoryUseCase,
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val updateMatchUseCase: UpdateMatchUseCase,
    private val endCurrentOpenMatchUseCase: EndCurrentOpenMatchUseCase,
    private val fetchCurrentOpenMatchUseCase: GetCurrentOpenMatchUseCase,
    private val registerMatchUseCase: RegisterMatchUseCase,
) {
    private val lifeChangeEvents = MutableSharedFlow<LifeChangeEvent>(extraBufferCapacity = 100)
    private val pendingLifeChanges = mutableMapOf<String, PendingLifeChange>()

    suspend fun process(
        action: MatchAction,
        getState: () -> MatchState,
        scope: CoroutineScope,
        dispatch: suspend (MatchAction) -> Unit
    ) {

        when (action) {
            is MatchAction.InitializeMatch -> {
                scope.launch {
                    startLifeObserver(
                        scope = scope,
                        getState = getState
                    )
                }
            }

            is MatchAction.OnChangeScheme -> {
                updateMatchScheme(
                    state = getState.invoke(),
                    schemeEnum = action.scheme,
                    dispatch = dispatch
                )
            }

            is MatchAction.OnBackgroundSelected -> {
                insertPlayerWithBackgroundUseCase(
                    action.player,
                    action.card
                )
            }

            is MatchAction.OnLifeChange -> {
                lifeChangeEvents.emit(
                    LifeChangeEvent(player = action.player, delta = action.delta)
                )

                onChangeLife(
                    state = getState.invoke(),
                    playerId = action.player.playerPosition.name,
                    delta = action.delta,
                    dispatch = dispatch
                )
            }

            is MatchAction.OnCommanderDamageChanged -> {
                onCommanderDamageChange(
                    state = getState.invoke(),
                    receivingPlayer = action.receivingPlayer,
                    playerName = action.playerName,
                    amount = action.amount,
                    dispatch = dispatch
                )
            }

            is MatchAction.OnInitialPlayerLoad -> {
                dispatch(
                    MatchAction.InitialPlayersLoaded(
                        players = getAllPlayersUseCase()
                    )
                )
            }

            is MatchAction.UpdateMatchData -> {
                updateMatchUseCase(
                    matchData = action.matchData
                )

                dispatch(
                    MatchAction.MatchUpdated(action.matchData)
                )
            }

            is MatchAction.RestartMatch -> {
                val currentState = getState.invoke()
                restartMatch(state = currentState, dispatch = dispatch)
            }

            is MatchAction.OnFetchCurrentMatch -> {
                val currentOpenMatch = fetchCurrentOpenMatchUseCase()
                if (currentOpenMatch == null) {
                    dispatch(MatchAction.OnRegisterMatch)
                } else {
                    val currentState = getState.invoke()
                    var currentMatch = currentState.matchData

                    currentMatch = currentMatch.copy(
                        id = currentOpenMatch.id,
                        players = currentOpenMatch.players,
                        gameScheme = currentOpenMatch.gameScheme,
                        createdAt = currentOpenMatch.createdAt
                    )

                    dispatch(
                        MatchAction.MatchUpdated(
                            updatedMatch = currentMatch
                        )
                    )
                }
            }

            is MatchAction.OnRegisterMatch -> {
                val currentState = getState.invoke()
                val playersList = currentState.players.take(
                    currentState.matchData.gameScheme.schemeEnum.numbersOfPlayers
                )

                val newMatchData = MatchData(
                    players = playersList.resetPlayerValues(currentState.matchData),
                    startingLife = currentState.matchData.startingLife,
                    gameScheme = currentState.matchData.gameScheme,
                    createdAt = System.currentTimeMillis(),
                )

                val currentMatchId = registerMatchUseCase(newMatchData)

                dispatch(
                    MatchAction.MatchUpdated(newMatchData.copy(id = currentMatchId))
                )
            }

            else -> Unit

        }
    }

    private suspend fun onCommanderDamageChange(
        state: MatchState,
        receivingPlayer: PlayerData, // Quem está recebendo o dano
        playerName: String,     // O ID de quem causou o dano
        amount: Int,
        dispatch: suspend (MatchAction) -> Unit
    ){
        val updatedPlayers = state.matchData.players.map { player ->
            if (player.name == receivingPlayer.name) {
                val playerWithUpdatedDamage = player.copy(
                    commanderDamageReceived = player.commanderDamageReceived.map { damageEntry ->
                        if (damageEntry.name == playerName) {
                            damageEntry.copy(
                                damage = maxOf(
                                    0,
                                    damageEntry.damage + amount
                                )
                            ) // Garante que o dano não seja negativo
                        } else {
                            damageEntry
                        }
                    }.toMutableList()
                )
                playerWithUpdatedDamage
            } else {
                player
            }
        }

        val currentMatch = state.matchData.copy(
            players = updatedPlayers
        )

        dispatch(MatchAction.MatchUpdated(currentMatch))
        updateMatchUseCase.invoke(currentMatch)
    }

    private suspend fun onChangeLife(
        state: MatchState,
        playerId: String,
        delta: Int,
        dispatch: suspend (MatchAction) -> Unit
    ) {
        var currentMatch = state.matchData

        val updatedPlayers = currentMatch.players.map { player ->
            if (player.playerPosition.name == playerId) {
                player.copy(life = player.life + delta)
            } else player
        }

        val updatedMatch = currentMatch.copy(
            players = updatedPlayers
        )

        currentMatch = updatedMatch

        dispatch(
            MatchAction.MatchUpdated(currentMatch)
        )

        // opcional: persistir
        updateMatchUseCase.invoke(currentMatch)
    }

    private suspend fun updateMatchScheme(
        state: MatchState,
        schemeEnum: SchemeEnum,
        dispatch: suspend (MatchAction) -> Unit
    ) {
        val gameScheme = state.matchData.gameScheme.copy(
            schemeEnum = schemeEnum,
            schemeName = schemeEnum.name
        )

        val playersList = state.players.take(gameScheme.schemeEnum.numbersOfPlayers)

        val currentState = state.copy(
            matchData = state.matchData.copy(
                players = playersList.resetPlayerValues(state.matchData),
                gameScheme = gameScheme
            )
        )

        updateMatchUseCase.invoke(currentState.matchData)

        dispatch(
            MatchAction.MatchUpdated(currentState.matchData)
        )
    }

    private suspend fun restartMatch(state: MatchState, dispatch: suspend (MatchAction) -> Unit) {
        val updatedPlayers = state.players.map { player ->
            // ✅ Usa selectedStartingLife (que vem do SharedPreferences)
            player.copy(
                life = state.selectedStartingLife,
                baseLife = state.selectedStartingLife,
                counters = getDefaultCounterData(),
                commanderDamageReceived = prepareCommanderDamage(
                    player = player,
                    state.players,
                    state.scheme?.numbersOfPlayers ?: SchemeEnum.SOLO.numbersOfPlayers
                )
            )
        }

        updatePlayersUseCase(updatedPlayers)

        endCurrentOpenMatchUseCase.invoke(
            matchId = state.matchData.id,
            finishedAt = System.currentTimeMillis()
        )

        dispatch(MatchAction.OnMatchRestarted(updatedPlayers = updatedPlayers))
        dispatch(MatchAction.OnFetchCurrentMatch)
    }

    private suspend fun registerMatchHistory(matchHistoryRegistry: MatchHistoryRegistry) {
        registerMatchHistoryUseCase.invoke(matchHistoryRegistry = matchHistoryRegistry)
    }

    private suspend fun flushLifeChangesToHistory(state: MatchState) {
        pendingLifeChanges.forEach { (playerId, change) ->
            if (change.totalDelta == 0) return@forEach

            registerMatchHistory(
                MatchHistoryRegistry(
                    matchId = state.matchData.id,
                    playerId = playerId,
                    delta = change.totalDelta,
                    lifeBefore = change.lifeBefore,
                    lifeAfter = change.lifeAfter,
                    timestamp = System.currentTimeMillis(),
                    source = if (change.totalDelta > 0)
                        MatchHistoryChangeSource.HEAL
                    else
                        MatchHistoryChangeSource.DAMAGE
                )
            )
        }

        pendingLifeChanges.clear()
    }

    private fun List<PlayerData>.resetPlayerValues(matchData: MatchData): List<PlayerData> {
        return map { player ->
            player.resetPlayerValues(matchData, this)
        }
    }

    private fun PlayerData.resetPlayerValues(
        matchData: MatchData,
        players: List<PlayerData>
    ): PlayerData {
        return PlayerData(
            playerPosition = playerPosition,
            name = name,
            life = matchData.startingLife,
            baseLife = matchData.startingLife,
            counters = getDefaultCounterData(),
            commanderDamageReceived = prepareCommanderDamage(
                player = this,
                players = players,
                players.size
            )
        )
    }

    //------------------------------------
    // Observers
    //------------------------------------
    @OptIn(FlowPreview::class)
    fun startLifeObserver(
        scope: CoroutineScope,
        getState: () -> MatchState
    ) {
        scope.launch {
            lifeChangeEvents
                .onEach { event ->
                    accumulateLifeChange(event)
                }
                .debounce(700)
                .collect {
                    val state = getState()
                    flushLifeChangesToHistory(state)
                }
        }
    }

    private fun accumulateLifeChange(event: LifeChangeEvent) {
        val playerKey = event.player.playerPosition.name
        val current = pendingLifeChanges[playerKey]

        if (current == null) {
            // Primeira mudança do ciclo
            pendingLifeChanges[playerKey] = PendingLifeChange(
                totalDelta = event.delta,
                lifeBefore = event.player.life,
                lifeAfter = event.player.life + event.delta
            )
        } else {
            // Já existe, acumula
            pendingLifeChanges[playerKey] = current.copy(
                totalDelta = current.totalDelta + event.delta,
                lifeAfter = current.lifeAfter + event.delta
            )
        }
    }

}