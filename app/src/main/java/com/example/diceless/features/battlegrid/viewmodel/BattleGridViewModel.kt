package com.example.diceless.features.battlegrid.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.diceless.domain.model.enums.SchemeEnum
import com.example.diceless.core.viewmodel.BaseViewModel
import com.example.diceless.data.repository.SettingsRepository
import com.example.diceless.domain.match.reducer.MatchAction
import com.example.diceless.domain.match.reducer.MatchMiddleware
import com.example.diceless.domain.match.reducer.MatchReducer
import com.example.diceless.domain.match.reducer.MatchState
import com.example.diceless.domain.match.reducer.MatchStore
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.LifeChangeEvent
import com.example.diceless.domain.model.MatchHistoryChangeSource
import com.example.diceless.domain.model.MatchHistoryRegistry
import com.example.diceless.domain.model.PendingLifeChange
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.usecase.RegisterMatchHistoryUseCase
import com.example.diceless.domain.usecase.UpdatePlayerUseCase
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.features.battlegrid.mvi.BattleGridState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleGridViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val updatePlayerUseCase: UpdatePlayerUseCase,
    private val registerMatchHistoryUseCase: RegisterMatchHistoryUseCase,
    private val reducer: MatchReducer,
    private val middleware: MatchMiddleware,
    private val initialMatchState: MatchState
) : BaseViewModel<BattleGridActions, Unit, BattleGridState>() { //ACTION, RESULT, STATE

    private val matchStore = MatchStore(
        reducer = reducer,
        middleware = middleware,
        initialState = initialMatchState,
        scope = viewModelScope
    )

    private val _state = MutableStateFlow(BattleGridState())
    val state: StateFlow<BattleGridState> = _state

    val matchState: StateFlow<MatchState> = matchStore.state

    private val lifeChangeEvents = MutableSharedFlow<LifeChangeEvent>(extraBufferCapacity = 100)
    private val pendingLifeChanges = mutableMapOf<String, PendingLifeChange>()

    override val initialState: BattleGridState
        get() = BattleGridState()

    override fun onAction(action: BattleGridActions) {
        when (action) {
            BattleGridActions.OnInit -> {
                onInitData()
            }

            is BattleGridActions.OnLifeIncreased -> {
                onLifeChange(player = action.player, amount = 1)
            }

            is BattleGridActions.OnLifeDecreased -> {
                onLifeChange(player = action.player, amount = -1)
            }

            is BattleGridActions.OnCounterSelected -> {
                selectCounter(action.player, action.counter)
            }

            is BattleGridActions.OnCounterToggled -> {
                toggleCounterState(action.player, action.counter)
            }

            is BattleGridActions.OnCounterIncrement -> {
                updateCounterValue(
                    player = action.player,
                    counterToToggle = action.counter,
                    updateValue = 1
                )
            }

            is BattleGridActions.OnCounterDecrement -> {
                updateCounterValue(
                    player = action.player,
                    counterToToggle = action.counter,
                    updateValue = -1
                )
            }

            is BattleGridActions.OnCommanderDamageChanged -> {
                // Aplica regras de config antes de mudar dano
                if (!state.value.allowSelfCommanderDamage &&
                    action.receivingPlayer.name == action.playerName
                ) {
                    return // Ignora se não permite dano a si mesmo
                }
                onCommanderDamageChange(
                    receivingPlayer = action.receivingPlayer,
                    playerName = action.playerName,
                    amount = action.amount
                )
            }

            BattleGridActions.OnRestart -> {
                restartMatch()
            }

            is BattleGridActions.OnStartingLifeChanged -> {
                updateStartingLife(action.life)
            }

            is BattleGridActions.OnAllowSelfCommanderDamageChanged -> {
                updateAllowSelfCommanderDamage(action.enabled)
            }

            is BattleGridActions.OnLinkCommanderDamageToLifeChanged -> {
                updateLinkCommanderDamageToLife(action.enabled)
            }

            is BattleGridActions.ToggleMonarchCounter -> {
                viewModelScope.launch {
                    matchStore.dispatch(MatchAction.ToggleMonarchCounter)
                }
            }

            is BattleGridActions.OnUpdateScheme -> {
                viewModelScope.launch {
                    matchStore.dispatch(MatchAction.OnChangeScheme(action.schemeEnum))
                }
            }

            is BattleGridActions.OnBackgroundSelected -> {
                updatePlayerBackground(action.player, action.card)
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Initial load
    // ---------------------------------------------------------------------------------------------

    private fun onInitData(){
        initializeMatchData()
        loadSettingsFromPrefs()
    }

    private fun initializeMatchData(){
        viewModelScope.launch {
            matchStore.dispatch(MatchAction.InitializeMatch)
            matchStore.dispatch(MatchAction.OnInitialPlayerLoad)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Settings
    // ---------------------------------------------------------------------------------------------

    private fun loadSettingsFromPrefs() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                selectedStartingLife = settingsRepository.getStartingLife(),
                allowSelfCommanderDamage = settingsRepository.getAllowSelfCommanderDamage(),
                linkCommanderDamageToLife = settingsRepository.getLinkCommanderDamageToLife()
            )
        }
    }

    private fun updateStartingLife(life: Int) {
        viewModelScope.launch {
            val newLife = life.coerceAtLeast(1) // Garante >= 1
            settingsRepository.saveStartingLife(newLife)
            _state.value = _state.value.copy(selectedStartingLife = newLife)
            _state.value.totalPlayers.forEach {
                updatePlayerUseCase(it.copy(baseLife = newLife))
            }
        }
    }

    private fun updateAllowSelfCommanderDamage(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveAllowSelfCommanderDamage(enabled)
            _state.value = _state.value.copy(allowSelfCommanderDamage = enabled)
        }
    }

    private fun updateLinkCommanderDamageToLife(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveLinkCommanderDamageToLife(enabled)
            _state.value = _state.value.copy(linkCommanderDamageToLife = enabled)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Game logic
    // ---------------------------------------------------------------------------------------------

    private fun restartMatch() {
        viewModelScope.launch {
            matchStore.dispatch(MatchAction.RestartMatch)
        }
    }

    private fun updatePlayerBackground(
        player: PlayerData,
        card: BackgroundProfileData
    ) {
        viewModelScope.launch {
            matchStore.dispatch(
                MatchAction.OnBackgroundSelected(
                    player = player,
                    card = card
                )
            )
        }
    }

    private fun updateGameScheme(
        schemeEnum: SchemeEnum
    ) {
        updateBattleGridState {
            selectedScheme = schemeEnum
        }

        restartMatch()
    }


    fun onLifeChange(player: PlayerData, amount: Int) {
        viewModelScope.launch {
            matchStore.dispatch(MatchAction.OnLifeChange(player = player, delta = amount))
        }
    }

    private fun toggleCounterState(player: PlayerData, counterToToggle: CounterData) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.activePlayers.map { p ->
                if (p.playerPosition == player.playerPosition) {
                    // Atualiza a lista de contadores para o jogador específico
                    val updatedCounters = p.counters.map { c ->
                        if (c.id == counterToToggle.id) {
                            c.toggleValue?.let { toggleValue ->
                                c.copy(toggleValue = !toggleValue) // Inverte o estado de seleção
                            } ?: c
                        } else {
                            c
                        }
                    }
                    registerCounterChange(player = p, counters = updatedCounters)
                    p.copy(counters = updatedCounters)
                } else {
                    p
                }
            }

            updatedPlayers.forEach {
                updatePlayerUseCase(it)
            }

            _state.value = _state.value.copy(activePlayers = updatedPlayers)
        }
    }

    private fun selectCounter(player: PlayerData, counterToToggle: CounterData) {
        viewModelScope.launch {
            matchStore.dispatch(
                MatchAction.OnCounterSelected(
                    player = player,
                    counter = counterToToggle
                )
            )
        }
    }

    private fun updateCounterValue(
        player: PlayerData,
        counterToToggle: CounterData,
        updateValue: Int
    ) {
        viewModelScope.launch {
            matchStore.dispatch(
                MatchAction.OnChangeCounterValue(
                    player = player,
                    counter = counterToToggle,
                    delta = updateValue
                )
            )
        }
    }

    private fun onCommanderDamageChange(
        receivingPlayer: PlayerData,
        playerName: String,
        amount: Int
    ) {
        viewModelScope.launch {
            matchStore.dispatch(
                MatchAction.OnCommanderDamageChanged(
                    receivingPlayer = receivingPlayer,
                    playerName = playerName,
                    amount = amount
                )
            )
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------------------------------
    private fun updateBattleGridState(properties: BattleGridState.() -> Unit) {
        viewModelScope.launch {
            val battleGridState = _state.value.copy().apply(properties)
            _state.emit(
                battleGridState
            )
        }
    }

    //-------------------------------------------------------------------------

    private fun registerLifeChange(player: PlayerData, amount: Int) {
        viewModelScope.launch {
            matchStore.dispatch(MatchAction.OnLifeChange(player, amount))
        }
    }

    private fun registerCounterChange(player: PlayerData, counters: List<CounterData>) {
        viewModelScope.launch {
            updatePlayerUseCase.invoke(
                player.copy(counters = counters)
            )
        }
    }

    private fun registerMatchHistory(matchHistoryRegistry: MatchHistoryRegistry) {
        viewModelScope.launch {
            registerMatchHistoryUseCase.invoke(matchHistoryRegistry = matchHistoryRegistry)
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

    private fun flushLifeChangesToHistory() {
        pendingLifeChanges.forEach { (playerId, change) ->
            if (change.totalDelta == 0) return@forEach
            Log.d("MatchData - ID", "${_state.value.matchData.id}")

            registerMatchHistory(
                MatchHistoryRegistry(
                    matchId = _state.value.matchData.id,
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

    override fun onCleared() {
        super.onCleared()

        viewModelScope.launch {
            flushLifeChangesToHistory()
        }
    }

    //------------------------------------
    // Observers
    //------------------------------------
    @OptIn(FlowPreview::class)
    private fun observeLifeDebounce() {
        viewModelScope.launch {
            lifeChangeEvents
                .onEach { event ->
                    accumulateLifeChange(event)
                }
                .debounce(700) // espera o usuário parar de clicar
                .collect {
                    flushLifeChangesToHistory()
                }
        }
    }
}

