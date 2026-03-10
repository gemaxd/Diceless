package com.example.diceless.domain.match.reducer

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.core.utils.prepareCommanderDamage
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.CounterData
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
import com.example.diceless.domain.usecase.UpdatePlayerUseCase
import com.example.diceless.domain.usecase.UpdatePlayersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchMiddleware @Inject constructor(
    private val updatePlayersUseCase: UpdatePlayersUseCase,
    private val updatePlayerUseCase: UpdatePlayerUseCase,
    private val registerMatchHistoryUseCase: RegisterMatchHistoryUseCase,
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val updateMatchUseCase: UpdateMatchUseCase,
    private val endCurrentOpenMatchUseCase: EndCurrentOpenMatchUseCase,
    private val fetchCurrentOpenMatchUseCase: GetCurrentOpenMatchUseCase,
    private val registerMatchUseCase: RegisterMatchUseCase,
    private val insertPlayerWithBackgroundUseCase: InsertPlayerWithBackgroundUseCase
) {
    private val lifeChangeEvents = MutableSharedFlow<LifeChangeEvent>(extraBufferCapacity = 100)
    private val pendingLifeChanges = mutableMapOf<String, PendingLifeChange>()
    private var lifeObserverStarted = false

    suspend fun process(
        action: MatchAction,
        getState: () -> MatchState,
        scope: CoroutineScope,
        dispatch: suspend (MatchAction) -> Unit
    ) {

        when (action) {
            is MatchAction.InitializeMatch -> {
                startLifeObserver(
                    scope = scope,
                    getState = getState
                )
            }

            is MatchAction.OnChangeScheme -> {
                updateMatchScheme(
                    state = getState.invoke(),
                    schemeEnum = action.scheme,
                    dispatch = dispatch
                )
            }

            is MatchAction.OnBackgroundSelected -> {
                onBackgroundSelected(
                    getState = getState,
                    player = action.player,
                    card = action.card,
                    dispatch = dispatch
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

            is MatchAction.OnCounterSelected -> {
                onCounterSelected(
                    state = getState.invoke(),
                    player = action.player,
                    counter = action.counter,
                    dispatch = dispatch
                )
            }

            is MatchAction.OnChangeCounterValue -> {
                onCounterValueChange(
                    state = getState.invoke(),
                    player = action.player,
                    counter = action.counter,
                    delta = action.delta,
                    dispatch = dispatch
                )
            }

            is MatchAction.OnInitialPlayerLoad -> {
                val players = getAllPlayersUseCase()

                dispatch(
                    MatchAction.InitialPlayersLoaded(
                        players = players
                    )
                )

                dispatch(MatchAction.OnFetchCurrentMatch)
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

    private suspend fun onBackgroundSelected(
        getState: () -> MatchState,
        player: PlayerData,
        card: BackgroundProfileData,
        dispatch: suspend (MatchAction) -> Unit
    ){
        val state = getState.invoke()
        val updatedPlayers = state.matchData.players.map {
            if (it.playerPosition == player.playerPosition) { // Usando uma chave única como a posição
                it.copy(backgroundProfile = card)
            } else {
                it
            }
        }

        val updatedReferencePlayers = state.players.map {
            if (it.playerPosition == player.playerPosition) { // Usando uma chave única como a posição
                it.copy(backgroundProfile = card)
            } else {
                it
            }
        }

        val updatedReferencePlayer = updatedReferencePlayers.first {
            player.playerPosition.name == it.playerPosition.name
        }

        insertPlayerWithBackgroundUseCase(
            updatedReferencePlayer, card
        )

        val currentMatch = state.matchData.copy(
            players = updatedPlayers
        )

        updateMatch(
            match = currentMatch,
            dispatch = dispatch,
            players = updatedReferencePlayers
        )
    }

    private suspend fun onCounterValueChange(
        state: MatchState,
        player: PlayerData,
        counter: CounterData,
        delta: Int,
        dispatch: suspend (MatchAction) -> Unit
    ){
        val updatedPlayers = state.matchData.players.map { p ->
            if (p.playerPosition == player.playerPosition) {
                // Atualiza a lista de contadores para o jogador específico
                val updatedCounters = p.counters.map { c ->
                    if (c.id == counter.id) {
                        c.value?.let { value ->
                            c.copy(value = value.plus(delta)) // Inverte o estado de seleção
                        } ?: c
                    } else {
                        c
                    }
                }
                p.copy(counters = updatedCounters)
            } else {
                p
            }
        }

        val currentMatchData = state.matchData.copy(
            players = updatedPlayers
        )

        updateMatch(match = currentMatchData, dispatch = dispatch)
    }

    private suspend fun onCounterSelected(
        state: MatchState,
        player: PlayerData,
        counter: CounterData,
        dispatch: suspend (MatchAction) -> Unit
    ){
        val updatedPlayers = state.matchData.players.map { p ->
            if (p.playerPosition == player.playerPosition) {
                // Atualiza a lista de contadores para o jogador específico
                val updatedCounters = p.counters.map { c ->
                    if (c.id == counter.id) {
                        c.copy(isSelected = !c.isSelected) // Inverte o estado de seleção
                    } else {
                        c
                    }
                }
                p.copy(counters = updatedCounters)
            } else {
                p
            }
        }

        val currentMatchData = state.matchData.copy(
            players = updatedPlayers
        )

        updateMatch(match = currentMatchData, dispatch = dispatch)
    }

    private suspend fun onCommanderDamageChange(
        state: MatchState,
        receivingPlayer: PlayerData, // Quem está recebendo o dano
        playerName: String,     // O ID de quem causou o dano
        amount: Int,
        dispatch: suspend (MatchAction) -> Unit
    ) {
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

        updateMatch(match = currentMatch, dispatch = dispatch)
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

        updateMatch(match = currentMatch, dispatch = dispatch)
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

        updateMatch(match = currentState.matchData, dispatch = dispatch)
        dispatch(MatchAction.RestartMatch)
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
            backgroundProfile = this.backgroundProfile,
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
    private fun startLifeObserver(
        scope: CoroutineScope,
        getState: () -> MatchState
    ) {
        if (lifeObserverStarted) return
        lifeObserverStarted = true

        scope.launch {
            lifeChangeEvents
                .onEach { accumulateLifeChange(it) }
                .debounce(700)
                .collect {
                    flushLifeChangesToHistory(getState())
                }
        }
    }

    private fun accumulateLifeChange(event: LifeChangeEvent) {
        val playerKey = event.player.playerPosition.name
        val current = pendingLifeChanges[playerKey]

        if (current == null) {
            pendingLifeChanges[playerKey] = PendingLifeChange(
                totalDelta = event.delta,
                lifeBefore = event.player.life,
                lifeAfter = event.player.life + event.delta
            )
        } else {
            pendingLifeChanges[playerKey] = current.copy(
                totalDelta = current.totalDelta + event.delta,
                lifeAfter = current.lifeAfter + event.delta
            )
        }
    }

    private suspend fun updateMatch(
        match: MatchData,
        players: List<PlayerData>? = null,
        dispatch: suspend (MatchAction) -> Unit
    ) {
        dispatch(MatchAction.MatchUpdated(match, players = players))
        updateMatchUseCase(match)
    }

}