package com.example.diceless.features.battlegrid.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.viewmodel.BaseViewModel
import com.example.diceless.data.repository.SettingsRepository
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.CommanderDamage
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.getDefaultCounterData
import com.example.diceless.domain.usecase.GetAllPlayersUseCase
import com.example.diceless.domain.usecase.GetGameSchemeUseCase
import com.example.diceless.domain.usecase.InsertPlayerWithBackgroundUseCase
import com.example.diceless.domain.usecase.RegisterMatchUseCase
import com.example.diceless.domain.usecase.SaveGameSchemeUseCase
import com.example.diceless.domain.usecase.UpdateMatchUseCase
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.features.battlegrid.mvi.BattleGridState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class BattleGridViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val insertPlayerWithBackgroundUseCase: InsertPlayerWithBackgroundUseCase,
    private val getGameSchemeUseCase: GetGameSchemeUseCase,
    private val saveGameSchemeUseCase: SaveGameSchemeUseCase,
    private val registerMatchUseCase: RegisterMatchUseCase,
    private val updateMatchUseCase: UpdateMatchUseCase
) : BaseViewModel<BattleGridActions, Unit, BattleGridState>() { //ACTION, RESULT, STATE
    private val _state = MutableStateFlow(BattleGridState())
    val state: StateFlow<BattleGridState> = _state
    private var initialDataIsLoaded = false
    private var matchIsStarted = false

    override val initialState: BattleGridState
        get() = BattleGridState()

    // 🔹 Init enxuto e previsível
    init {
        loadSettingsFromPrefs()
        initialLoadIfNeeded()
        observePlayers()
        observeScheme()
    }

    override fun onAction(action: BattleGridActions) {
        when (action) {

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
                val toggle = state.value.showMonarchSymbol
                updateBattleGridState {
                    showMonarchSymbol = !toggle
                }
            }

            is BattleGridActions.OnUpdateScheme -> {
                updateGameScheme(action.schemeEnum)
                updateMatchData(action.schemeEnum.numbersOfPlayers)
                restartMatch()
            }

            is BattleGridActions.OnBackgroundSelected -> {
                updatePlayerBackground(action.player, action.card)
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Initial load
    // ---------------------------------------------------------------------------------------------

    private var initialized = false

    private fun initialLoadIfNeeded() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            val players = getAllPlayersUseCase().first()

            if (players.isEmpty()) {
                (0 until 4).forEach { index ->
                    val playerNumber = index + 1
                    insertPlayerWithBackgroundUseCase(
                        player = PlayerData(
                            name = "Player $playerNumber",
                            playerPosition = PositionEnum.getPosition(playerNumber)
                        ),
                        background = null
                    )
                }
            }
        }
    }

    private fun observeScheme() {
        viewModelScope.launch {
            Log.d("observeScheme-Begin", "MATCH-DATA-ID: ${_state.value.matchData.id}")
            getGameSchemeUseCase()
                .collect { schemeFromDB ->
                    _state.update { current ->
                        val activePlayers = current.totalPlayers.take(
                            schemeFromDB?.schemeEnum?.numbersOfPlayers ?: GameSchemeData().schemeEnum.numbersOfPlayers
                        )

                        current.copy(
                            activePlayers = activePlayers,
                            selectedScheme = schemeFromDB?.schemeEnum ?: GameSchemeData().schemeEnum
                        )
                    }
                    restartMatch()

                    if(!matchIsStarted){
                        matchIsStarted = true
                        registerMatch()
                    }

                    Log.d("observeScheme-End", "MATCH-DATA-ID: ${_state.value.matchData.id}")
            }
        }
    }

    private fun registerMatch() {
        viewModelScope.launch {
            val currentMatchId = registerMatchUseCase(MatchData(playersCount = _state.value.selectedScheme.numbersOfPlayers))
            val currentMatch = MatchData(
                id = currentMatchId,
                playersCount = _state.value.activePlayers.size
            )

            _state.update { current ->
                current.copy(
                    matchData = currentMatch
                )
            }
        }
    }

    private fun updateMatchData(quantity: Int){
        viewModelScope.launch {
            updateMatchUseCase(
                _state.value.matchData.copy(
                    playersCount = quantity
                )
            )
        }
    }

    private fun observePlayers() {
        viewModelScope.launch {
            Log.d("BattleGridViewModel-observePlayers-Begin", "MATCH-DATA-ID: ${_state.value.matchData.id}")
            getAllPlayersUseCase().collect { playersFromDb ->

                _state.update { current ->

                    // 1️⃣ Bootstrap inicial
                    if (!initialDataIsLoaded && playersFromDb.isNotEmpty()) {
                        initialDataIsLoaded = true

                        val initialPlayers = playersFromDb
                            .take(current.selectedScheme.numbersOfPlayers)
                            .map { player ->
                                player.copy(
                                    life = current.selectedStartingLife,
                                    baseLife = current.selectedStartingLife,
                                    counters = getDefaultCounterData(),
                                    commanderDamageReceived = prepareCommanderDamage(
                                        player,
                                        playersFromDb,
                                        current.selectedScheme.numbersOfPlayers
                                    )
                                )
                            }

                        return@update current.copy(
                            activePlayers = initialPlayers,
                            totalPlayers = playersFromDb
                        )
                    }

                    // 2️⃣ Sincronização normal (sem reset)
                    if (current.activePlayers.isEmpty()) {
                        return@update current.copy(
                            totalPlayers = playersFromDb
                        )
                    }

                    val syncedPlayers = current.activePlayers.map { local ->
                        val persisted = playersFromDb.firstOrNull {
                            it.playerPosition == local.playerPosition
                        }

                        persisted?.let {
                            local.copy(
                                name = it.name,
                                backgroundProfile = it.backgroundProfile
                            )
                        } ?: local
                    }

                    current.copy(
                        activePlayers = syncedPlayers,
                        totalPlayers = playersFromDb
                    )
                }
            }
            Log.d("BattleGridViewModel-observePlayers-End", "MATCH-DATA-ID: ${_state.value.matchData.id}")
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
        Log.d("BattleGridViewModel-RestartMatch-Begin", "MATCH-DATA-ID: ${_state.value.matchData.id}")

        val restartedPlayerList =
            _state.value.totalPlayers.take(_state.value.selectedScheme.numbersOfPlayers)

        val updatedPlayers = restartedPlayerList.map { player ->
            // ✅ Usa selectedStartingLife (que vem do SharedPreferences)
            player.copy(
                life = _state.value.selectedStartingLife,
                baseLife = _state.value.selectedStartingLife,
                counters = getDefaultCounterData(),
                commanderDamageReceived = prepareCommanderDamage(
                    player = player,
                    restartedPlayerList,
                    _state.value.selectedScheme.numbersOfPlayers
                )
            )
        }
        _state.value = _state.value.copy(activePlayers = updatedPlayers)

        Log.d("BattleGridViewModel-RestartMatch-End", "MATCH-DATA-ID: ${_state.value.matchData.id}")
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

            delay(1000)

            // 2️⃣ Persiste em paralelo
            insertPlayerWithBackgroundUseCase(
                player,
                card
            )
        }
    }

    private fun updateGameScheme(
        schemeEnum: SchemeEnum
    ) {
        viewModelScope.launch {
            Log.d("BattleGridViewModel-updateGameScheme-Begin", "MATCH-DATA-ID: ${_state.value.matchData.id}")
            saveGameSchemeUseCase.invoke(
                GameSchemeData(
                    schemeEnum = schemeEnum,
                    schemeName = schemeEnum.name
                )
            )
            Log.d("BattleGridViewModel-updateGameScheme-End", "MATCH-DATA-ID: ${_state.value.matchData.id}")
        }
    }


    fun onLifeChange(player: PlayerData, amount: Int) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.activePlayers.map {
                if (it.playerPosition == player.playerPosition) { // Usando uma chave única como a posição
                    it.copy(life = it.life + amount)
                } else {
                    it
                }
            }
            _state.value = _state.value.copy(activePlayers = updatedPlayers)
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
                    p.copy(counters = updatedCounters)
                } else {
                    p
                }
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


    private fun prepareCommanderDamage(
        player: PlayerData,
        players: List<PlayerData>,
        numberOfPlayers: Int
    ): MutableList<CommanderDamage> {

        val opponents =
            players.take(numberOfPlayers).filter { it.name != player.name }

        return opponents.map {
            CommanderDamage(name = it.name, damage = 0)
        }.toMutableList()
    }
}

