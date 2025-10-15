package com.example.diceless.ui.battlegrid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.mvi.BattleGridState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleGridViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(BattleGridState())
    val state: StateFlow<BattleGridState> = _state

    init {
        prepareFakeData()
    }

    fun onAction(action: BattleGridActions){
        when(action){
            is BattleGridActions.OnLifeIncreased -> {
                onLifeChange(player = action.player, amount = 1)
            }
            is BattleGridActions.OnLifeDecreased -> {
                onLifeChange(player = action.player, amount = -1)
            }
            is BattleGridActions.OnCounterToggled -> {
                toggleCounterState(action.player, action.counter)
            }
        }
    }

    fun onLifeChange(player: PlayerData, amount: Int){
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

    private fun prepareFakeData(){
        val players = listOf(
            PlayerData(name ="Jogador 1", playerPosition = PositionEnum.PLAYER_ONE),
            PlayerData(name ="Jogador 2", playerPosition = PositionEnum.PLAYER_TWO),
            PlayerData(name ="Jogador 3", playerPosition = PositionEnum.PLAYER_THREE),
            PlayerData(name ="Jogador 4", playerPosition = PositionEnum.PLAYER_FOUR)
        )

        val selectedScheme = SchemeEnum.TRIPLE_STANDARD

        _state.value = _state.value.copy(
            players = players,
            selectedScheme = selectedScheme
        )
    }
}
