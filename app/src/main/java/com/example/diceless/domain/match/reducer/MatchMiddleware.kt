package com.example.diceless.domain.match.reducer

import android.R.attr.scheme
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.extensions.toHistoryPlayerBasicDataList
import com.example.diceless.core.utils.prepareCommanderDamage
import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.model.LifeChangeEvent
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryChangeSource
import com.example.diceless.domain.model.MatchHistoryRegistry
import com.example.diceless.domain.model.PendingLifeChange
import com.example.diceless.domain.model.getDefaultCounterData
import com.example.diceless.domain.usecase.EndCurrentOpenMatchUseCase
import com.example.diceless.domain.usecase.GetAllPlayersUseCase
import com.example.diceless.domain.usecase.GetCurrentOpenMatchUseCase
import com.example.diceless.domain.usecase.GetGameSchemeUseCase
import com.example.diceless.domain.usecase.InsertPlayerWithBackgroundUseCase
import com.example.diceless.domain.usecase.RegisterMatchHistoryUseCase
import com.example.diceless.domain.usecase.RegisterMatchUseCase
import com.example.diceless.domain.usecase.SaveGameSchemeUseCase
import com.example.diceless.domain.usecase.UpdateMatchUseCase
import com.example.diceless.domain.usecase.UpdatePlayerUseCase
import com.example.diceless.domain.usecase.UpdatePlayersUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MatchMiddleware @Inject constructor(
    private val saveGameSchemeUseCase: SaveGameSchemeUseCase,
    private val insertPlayerWithBackgroundUseCase: InsertPlayerWithBackgroundUseCase,
    private val updatePlayerUseCase: UpdatePlayerUseCase,
    private val updatePlayersUseCase: UpdatePlayersUseCase,
    private val getSchemeUseCase: GetGameSchemeUseCase,
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
        state: MatchState,
        dispatch: suspend (MatchAction) -> Unit
    ) {

        when (action) {
            is MatchAction.ChangeScheme -> {
                saveGameSchemeUseCase(
                    GameSchemeData(
                        schemeEnum = action.scheme,
                        schemeName = action.scheme.name
                    )
                )
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

                updatePlayerUseCase(
                    action.player.copy(life = action.delta)
                )

                dispatch(MatchAction.OnLifeChanged(action.player, action.delta))
            }

            is MatchAction.InitializeMatch -> {
                val schemeData = getSchemeUseCase()

                dispatch(
                    MatchAction.SchemeLoaded(
                        schemeData?.schemeEnum ?: SchemeEnum.SOLO
                    )
                )
            }

            is MatchAction.OnInitialPlayerLoad -> {
                val initialPlayers = getAllPlayersUseCase()

                dispatch(
                    MatchAction.InitialPlayersLoaded(
                        players = initialPlayers
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

            is MatchAction.FetchCurrentOpenMatch -> {
                val currentOpenMatch = fetchCurrentOpenMatchUseCase()
                if (currentOpenMatch == null){
                    dispatch(MatchAction.RestartMatch)
                } else {
                    val currentMatch = MatchData(
                        id = currentOpenMatch.id,
                        players = state.players.toHistoryPlayerBasicDataList()
                    )

                    dispatch(
                        MatchAction.OnCurrentMatchFetched(
                            matchData = currentMatch
                        )
                    )
                }
            }

            is MatchAction.RegisterMatch -> {
                var newMatchData = MatchData(
                    players = state.players.toHistoryPlayerBasicDataList(),
                    startingLife = state.selectedStartingLife
                )

                val currentMatchId = registerMatchUseCase(newMatchData)

                newMatchData = newMatchData.copy(id = currentMatchId)

                dispatch(
                    MatchAction.OnMatchRegistered(newMatchData)
                )
            }

            else -> Unit

        }
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
        dispatch(MatchAction.FetchCurrentOpenMatch)
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