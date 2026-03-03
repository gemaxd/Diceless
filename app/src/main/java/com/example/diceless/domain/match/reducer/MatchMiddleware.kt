package com.example.diceless.domain.match.reducer

import android.util.Log
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.core.utils.prepareCommanderDamage
import com.example.diceless.domain.model.GameSchemeData
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MatchMiddleware @Inject constructor(
    private val insertPlayerWithBackgroundUseCase: InsertPlayerWithBackgroundUseCase,
    private val updatePlayerUseCase: UpdatePlayerUseCase,
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
    private var currentMatch: MatchData = MatchData()
    private var totalPlayers: List<PlayerData> = emptyList()

    suspend fun process(
        action: MatchAction,
        state: MatchState,
        dispatch: suspend (MatchAction) -> Unit
    ) {

        when (action) {
            is MatchAction.InitializeMatch -> { }

            is MatchAction.OnChangeScheme -> {
                updateMatchScheme(action.scheme, dispatch = dispatch)
            }

            is MatchAction.OnBackgroundSelected -> {
                insertPlayerWithBackgroundUseCase(
                    action.player,
                    action.card
                )
            }

            is MatchAction.ToggleMonarchCounter -> {}
            is MatchAction.OnLifeChange -> {
                lifeChangeEvents.emit(
                    LifeChangeEvent(player = action.player, delta = action.delta)
                )

                onChangeLife(playerId = action.player.playerPosition.name, delta = action.delta, dispatch = dispatch)
            }



            is MatchAction.OnInitialPlayerLoad -> {
                totalPlayers = getAllPlayersUseCase()

                dispatch(
                    MatchAction.InitialPlayersLoaded(
                        players = totalPlayers
                    )
                )
            }

            is MatchAction.UpdateMatchData -> {
                updateMatchUseCase(
                    matchData = action.matchData
                )

                dispatch(
                    MatchAction.UpdateMatchState(action.matchData)
                )
            }

            is MatchAction.RestartMatch -> {
                restartMatch(state = state, dispatch = dispatch)
            }

            is MatchAction.OnFetchCurrentMatch -> {
                val currentOpenMatch = fetchCurrentOpenMatchUseCase()
                if (currentOpenMatch == null){
                    dispatch(MatchAction.OnRegisterMatch)
                } else {
                    currentMatch = currentMatch.copy(
                        id = currentOpenMatch.id,
                        players = currentOpenMatch.players,
                        gameScheme = currentOpenMatch.gameScheme,
                        createdAt = currentOpenMatch.createdAt
                    )

                    dispatch(
                        MatchAction.CurrentMatchFetched(
                            matchData = currentMatch
                        )
                    )
                }
            }

            is MatchAction.OnRegisterMatch -> {
                val playersList = totalPlayers.take(currentMatch.gameScheme.schemeEnum.numbersOfPlayers)

                val newMatchData = MatchData(
                    players = playersList.resetPlayerValues(currentMatch),
                    startingLife = currentMatch.startingLife,
                    gameScheme = currentMatch.gameScheme,
                    createdAt = System.currentTimeMillis(),
                )

                val scheme = GameSchemeData()
                Log.d("GameScheme: ", Json.encodeToString(scheme))

                val currentMatchId = registerMatchUseCase(newMatchData)

                currentMatch = newMatchData.copy(id = currentMatchId)

                dispatch(
                    MatchAction.MatchRegistered(currentMatch)
                )
            }

            else -> Unit

        }
    }

    private suspend fun onChangeLife(playerId: String, delta: Int, dispatch: suspend (MatchAction) -> Unit){
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

    private suspend fun updateMatchScheme(schemeEnum: SchemeEnum, dispatch: suspend (MatchAction) -> Unit){
        val gameScheme = currentMatch.gameScheme.copy(
            schemeEnum = schemeEnum,
            schemeName = schemeEnum.name
        )

        val playersList = totalPlayers.take(gameScheme.schemeEnum.numbersOfPlayers)

        currentMatch = currentMatch.copy(
            players = playersList.resetPlayerValues(currentMatch),
            gameScheme = gameScheme
        )

        updateMatchUseCase.invoke(currentMatch)

        dispatch(
            MatchAction.MatchUpdated(currentMatch)
        )
    }

    private suspend fun restartMatch(state: MatchState, dispatch: suspend (MatchAction) -> Unit){
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

    private fun PlayerData.resetPlayerValues(matchData: MatchData, players: List<PlayerData>): PlayerData {
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
    private suspend fun observeLifeDebounce(state: MatchState) {
            lifeChangeEvents
                .onEach { event ->
                    accumulateLifeChange(event)
                }
                .debounce(700)
                .collect {
                    flushLifeChangesToHistory(state)
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