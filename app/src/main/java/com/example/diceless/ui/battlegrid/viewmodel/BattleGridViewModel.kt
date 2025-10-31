package com.example.diceless.ui.battlegrid.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.data.SettingsRepository
import com.example.diceless.domain.model.CommanderDamage
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.getDefaultCounterData
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.mvi.BattleGridState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleGridViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<BattleGridActions, Unit, BattleGridState>() { //ACTION, RESULT, STATE

    private val _state = MutableStateFlow(BattleGridState())
    val state: StateFlow<BattleGridState> = _state

    override val initialState: BattleGridState
        get() = BattleGridState()

    init {
        prepareFakeData()
        loadSettingsFromPrefs() // Carrega configs do SharedPreferences
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
                    action.receivingPlayer.name == action.playerName) {
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
        }
    }

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

    private fun restartMatch() {
        viewModelScope.launch {
            val updatedPlayers = _state.value.players.map { player ->
                // ✅ Usa selectedStartingLife (que vem do SharedPreferences)
                player.copy(
                    life = _state.value.selectedStartingLife,
                    baseLife = _state.value.selectedStartingLife,
                    counters = getDefaultCounterData(),
                    commanderDamageReceived = prepareCommanderDamage(
                        _state.value.players,
                        _state.value.selectedScheme.numbersOfPlayers
                    )
                )
            }
            _state.value = _state.value.copy(players = updatedPlayers)
        }
    }

    fun onLifeChange(player: PlayerData, amount: Int) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.players.map {
                if (it.playerPosition == player.playerPosition) { // Usando uma chave única como a posição
                    it.copy(life = it.life + amount)
                } else {
                    it
                }
            }
            _state.value = _state.value.copy(players = updatedPlayers)
        }
    }

    private fun toggleCounterState(player: PlayerData, counterToToggle: CounterData) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.players.map { p ->
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
            _state.value = _state.value.copy(players = updatedPlayers)
        }
    }

    private fun selectCounter(player: PlayerData, counterToToggle: CounterData) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.players.map { p ->
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
            _state.value = _state.value.copy(players = updatedPlayers)
        }
    }

    private fun updateCounterValue(player: PlayerData, counterToToggle: CounterData, updateValue: Int) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.players.map { p ->
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
            _state.value = _state.value.copy(players = updatedPlayers)
        }
    }

    private fun onCommanderDamageChange(
        receivingPlayer: PlayerData,
        playerName: String,
        amount: Int
    ) {
        viewModelScope.launch {
            val updatedPlayers = _state.value.players.map { player ->
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
            _state.value = _state.value.copy(players = updatedPlayers)
        }
    }

    private fun prepareCommanderDamage(
        players: List<PlayerData>,
        numberOfPlayers: Int
    ): MutableList<CommanderDamage> {
        val playersBasedOnScheme = players.take(numberOfPlayers)
        var commanderDamage = mutableListOf<CommanderDamage>()

        playersBasedOnScheme.map { currentPlayer ->
            val opponents = playersBasedOnScheme.filter { it.name != currentPlayer.name }

            commanderDamage = opponents.map { opponent ->
                CommanderDamage(name = opponent.name, damage = 0)
            }.toMutableList()
        }
        return commanderDamage
    }

    private fun updateBattleGridState(properties: BattleGridState.() -> Unit) {
        viewModelScope.launch {
            val battleGridState = _state.value.copy().apply(properties)
            _state.emit(
                battleGridState
            )
        }
    }

    private fun prepareFakeData() {
        val players = listOf(
            PlayerData(name = "Jogador 1", playerPosition = PositionEnum.PLAYER_ONE),
            PlayerData(name = "Jogador 2", playerPosition = PositionEnum.PLAYER_TWO),
            PlayerData(name = "Jogador 3", playerPosition = PositionEnum.PLAYER_THREE),
            PlayerData(name = "Jogador 4", playerPosition = PositionEnum.PLAYER_FOUR)
        )

        val selectedScheme = SchemeEnum.QUADRA_STANDARD
        val playersBasedOnScheme = players.take(selectedScheme.numbersOfPlayers)
        val playersWithCommanderDamage = playersBasedOnScheme.map { currentPlayer ->
            val damageTrackers = prepareCommanderDamage(players, selectedScheme.numbersOfPlayers)

            currentPlayer.copy(commanderDamageReceived = damageTrackers)
        }

        _state.value = _state.value.copy(
            players = playersWithCommanderDamage,
            selectedScheme = selectedScheme
        )
    }
}
