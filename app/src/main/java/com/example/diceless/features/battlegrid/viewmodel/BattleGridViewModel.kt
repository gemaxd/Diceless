package com.example.diceless.features.battlegrid.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.extensions.toHistoryPlayerBasicDataList
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.core.utils.prepareCommanderDamage
import com.example.diceless.data.repository.SettingsRepository
import com.example.diceless.domain.match.reducer.MatchAction
import com.example.diceless.domain.match.reducer.MatchState
import com.example.diceless.domain.match.reducer.MatchStore
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.CounterData
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
import com.example.diceless.domain.usecase.GetGameSchemeUseCase
import com.example.diceless.domain.usecase.InsertPlayerWithBackgroundUseCase
import com.example.diceless.domain.usecase.RegisterMatchHistoryUseCase
import com.example.diceless.domain.usecase.RegisterMatchUseCase
import com.example.diceless.domain.usecase.SaveGameSchemeUseCase
import com.example.diceless.domain.usecase.UpdateMatchUseCase
import com.example.diceless.domain.usecase.UpdatePlayerUseCase
import com.example.diceless.domain.usecase.UpdatePlayersUseCase
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.features.battlegrid.mvi.BattleGridState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleGridViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val registerMatchUseCase: RegisterMatchUseCase,
    private val updateMatchUseCase: UpdateMatchUseCase,
    private val fetchCurrentOpenMatchUseCase: GetCurrentOpenMatchUseCase,
    private val endCurrentOpenMatchUseCase: EndCurrentOpenMatchUseCase,
    private val updatePlayerUseCase: UpdatePlayerUseCase,
    private val updatePlayersUseCase: UpdatePlayersUseCase,
    private val registerMatchHistoryUseCase: RegisterMatchHistoryUseCase,
    private val matchStore: MatchStore
) : BaseViewModel<BattleGridActions, Unit, BattleGridState>() { //ACTION, RESULT, STATE
    private val _state = MutableStateFlow(BattleGridState())
    val state: StateFlow<BattleGridState> = _state

    val matchState: StateFlow<MatchState> = matchStore.state

    private val lifeChangeEvents = MutableSharedFlow<LifeChangeEvent>(extraBufferCapacity = 100)
    private val pendingLifeChanges = mutableMapOf<String, PendingLifeChange>()
    private var initialDataIsLoaded = false
    private var matchIsStarted = false

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
                    matchStore.dispatch(MatchAction.ChangeScheme(action.schemeEnum))
                }

                updateGameScheme(action.schemeEnum)
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
        initializeSchemeData()
        loadSettingsFromPrefs()
        fetchCurrentOpenMatch()
        initialPlayerLoad()
        observeLifeDebounce()
    }

    private fun initializeSchemeData(){
        viewModelScope.launch {
            matchStore.dispatch(MatchAction.InitializeMatch)
        }
    }

    private fun initialPlayerLoad() {
        viewModelScope.launch {
            matchStore.dispatch(MatchAction.OnInitialPlayerLoad)

//            getAllPlayersUseCase().collect { playersFromDb ->
//                _state.update { current ->
//                    // 1️⃣ Bootstrap inicial
//                    if (!initialDataIsLoaded && playersFromDb.isNotEmpty()) {
//                        initialDataIsLoaded = true
//
//                        matchState.value.scheme?.let { selectedScheme ->
//                            val initialPlayers = playersFromDb
//                                .take(selectedScheme.numbersOfPlayers)
//                                .map { player ->
//                                    player.copy(
//                                        life = player.life,
//                                        baseLife = player.baseLife,
//                                        counters = player.counters,
//                                        commanderDamageReceived = player.commanderDamageReceived
//                                    )
//                                }
//
//                            return@update current.copy(
//                                activePlayers = initialPlayers,
//                                totalPlayers = playersFromDb
//                            )
//                        }
//                    }
//
//                    // 2️⃣ Sincronização normal (sem reset)
//                    if (current.activePlayers.isEmpty()) {
//                        return@update current.copy(
//                            totalPlayers = playersFromDb
//                        )
//                    }
//
//                    val syncedPlayers = current.activePlayers.map { local ->
//                        val persisted = playersFromDb.firstOrNull {
//                            it.playerPosition == local.playerPosition
//                        }
//
//                        persisted?.let {
//                            local.copy(
//                                name = it.name,
//                                backgroundProfile = it.backgroundProfile
//                            )
//                        } ?: local
//                    }
//
//                    updateMatchData(
//                        matchData = _state.value.matchData.copy(
//                            players = syncedPlayers.toHistoryPlayerBasicDataList()
//                        )
//                    )
//
//                    current.copy(
//                        activePlayers = syncedPlayers,
//                        totalPlayers = playersFromDb
//                    )
//                }
//                matchIsStarted = true
//            }
        }
    }

    private var initialized = false

    private fun registerMatch() {
        viewModelScope.launch {
            var newMatchData = MatchData(
                players = _state.value.activePlayers.toHistoryPlayerBasicDataList(),
                startingLife = _state.value.selectedStartingLife
            )

            val currentMatchId = registerMatchUseCase(newMatchData)

            newMatchData = newMatchData.copy(id = currentMatchId)

            _state.update { current ->
                current.copy(
                    matchData = newMatchData
                )
            }
        }
    }

    private fun fetchCurrentOpenMatch(){
        viewModelScope.launch {
            val currentOpenMatch = fetchCurrentOpenMatchUseCase()
            if (currentOpenMatch == null){
                registerMatch()
            } else {
                val currentMatch = MatchData(
                    id = currentOpenMatch.id,
                    players = _state.value.activePlayers.toHistoryPlayerBasicDataList()
                )

                _state.update { current ->
                    current.copy(
                        matchData = currentMatch
                    )
                }
            }
        }
    }

    private fun updateMatchData(matchData: MatchData){
        viewModelScope.launch {
            _state.update { current ->
                current.copy(
                    matchData = matchData
                )
            }

            updateMatchUseCase(
                _state.value.matchData
            )
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
//            val restartedPlayerList = _state.value.totalPlayers
//
//            _state.value.selectedScheme?.let { scheme ->
//                val updatedPlayers = restartedPlayerList.map { player ->
//                    // ✅ Usa selectedStartingLife (que vem do SharedPreferences)
//                    player.copy(
//                        life = _state.value.selectedStartingLife,
//                        baseLife = _state.value.selectedStartingLife,
//                        counters = getDefaultCounterData(),
//                        commanderDamageReceived = prepareCommanderDamage(
//                            player = player,
//                            restartedPlayerList,
//                            scheme.numbersOfPlayers
//                        )
//                    )
//                }
//
//                updatePlayersUseCase(updatedPlayers)
//
//                val takenPlayers = updatedPlayers.take(scheme.numbersOfPlayers)
//
//                updateMatchData(
//                    matchData = _state.value.matchData.copy(
//                        players = takenPlayers.toHistoryPlayerBasicDataList()
//                    )
//                )
//
//                _state.value = _state.value.copy(
//                    matchFinished = false,
//                    winnerId = null,
//                    activePlayers = takenPlayers,
//                    selectedScheme = _state.value.selectedScheme
//                )
//
//                endCurrentOpenMatchUseCase.invoke(
//                    matchId = _state.value.matchData.id,
//                    finishedAt = System.currentTimeMillis()
//                )
//
//                fetchCurrentOpenMatch()
//
//            }
        }
    }

    private fun updatePlayerBackground(
        player: PlayerData,
        card: BackgroundProfileData
    ) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.activePlayers.map {
                if (it.playerPosition == player.playerPosition) { // Usando uma chave única como a posição
                    it.copy(backgroundProfile = card)
                } else {
                    it
                }
            }
            _state.value = _state.value.copy(activePlayers = updatedPlayers)

            // 2️⃣ Persiste em paralelo
            matchStore.dispatch(MatchAction.OnBackgroundSelected(player = player, card = card))
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
//            val updatedPlayers = _state.value.activePlayers.map {
//                if (it.playerPosition == player.playerPosition) {
//                    // Usando uma chave única como a posição
//                    registerLifeChange(player = it, amount = (it.life + amount))
//                    it.copy(life = it.life + amount)
//                } else {
//                    it
//                }
//            }
//
//            _state.value = _state.value.copy(activePlayers = updatedPlayers)
//
//            lifeChangeEvents.emit(
//                LifeChangeEvent(player = player, delta = amount)
//            )
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
            val updatedPlayers = _state.value.activePlayers.map { p ->
                if (p.playerPosition == player.playerPosition) {
                    // Atualiza a lista de contadores para o jogador específico
                    val updatedCounters = p.counters.map { c ->
                        if (c.id == counterToToggle.id) {
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

            updatedPlayers.forEach {
                updatePlayerUseCase(it)
            }

            _state.value = _state.value.copy(activePlayers = updatedPlayers)
        }
    }

    private fun updateCounterValue(
        player: PlayerData,
        counterToToggle: CounterData,
        updateValue: Int
    ) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.activePlayers.map { p ->
                if (p.playerPosition == player.playerPosition) {
                    // Atualiza a lista de contadores para o jogador específico
                    val updatedCounters = p.counters.map { c ->
                        if (c.id == counterToToggle.id) {
                            c.value?.let { value ->
                                c.copy(value = value.plus(updateValue)) // Inverte o estado de seleção
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

            updatedPlayers.forEach {
                updatePlayerUseCase(it)
            }

            _state.value = _state.value.copy(activePlayers = updatedPlayers)
        }
    }

    private fun onCommanderDamageChange(
        receivingPlayer: PlayerData,
        playerName: String,
        amount: Int
    ) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.activePlayers.map { player ->
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

            updatedPlayers.forEach {
                updatePlayerUseCase(it)
            }

            _state.value = _state.value.copy(activePlayers = updatedPlayers)
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

