package com.example.diceless.features.bottombelt.options.diceroll.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceless.R
import com.example.diceless.domain.model.DiceType
import com.example.diceless.features.bottombelt.options.diceroll.mvi.DiceRollActions
import com.example.diceless.features.bottombelt.options.diceroll.mvi.DiceRollState
import com.example.diceless.features.bottombelt.options.diceroll.mvi.DiceUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiceRollViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(DiceRollState())
    val state: StateFlow<DiceRollState> = _state

    init {
        loadDice()
    }

    private fun loadDice() {
        val diceTypes = listOf(
            DiceType(id = "1", label = "d4", 4, diceRes = R.drawable.d4_icon),
            DiceType(id = "2", label = "d6", 6, diceRes = R.drawable.d6_icon),
            DiceType(id = "3", label = "d8", 8, diceRes = R.drawable.d8_icon),
            DiceType(id = "4", label = "d10", 10, diceRes = R.drawable.d10_icon),
            DiceType(id = "5", label = "d12", 12, diceRes = R.drawable.d12_icon),
            DiceType(id = "6", label = "d20", 20, diceRes = R.drawable.d20_icon),
            DiceType(id = "0", label = "Coin", null)
        )

        _state.value = DiceRollState(
            diceList = diceTypes.map { DiceUIState(it) }
        )
    }

    fun onAction(action: DiceRollActions) {
        when (action) {
            is DiceRollActions.OnDiceClicked -> rollDice(action.diceId)
        }
    }

    private fun rollDice(diceId: String) {
        val diceState = _state.value.diceList.firstOrNull {
            it.dice.id == diceId
        } ?: return

        if (diceState.rolling) return

        viewModelScope.launch {

            // 1. marca como rolling
            updateDice(diceId) {
                it.copy(rolling = true)
            }

            // 2. sorteia resultado
            val rolledValue =
                if (diceState.dice.sides == null) {
                    if ((0..1).random() == 0) "Heads" else "Tails"
                } else {
                    (1..diceState.dice.sides!!).random().toString()
                }

            delay(400)

            // 3. mostra resultado
            updateDice(diceId) {
                it.copy(result = rolledValue)
            }

            delay(2000)

            // 4. limpa resultado e volta dado
            updateDice(diceId) {
                it.copy(result = null, rolling = false)
            }
        }
    }

    private fun updateDice(
        diceId: String,
        transform: (DiceUIState) -> DiceUIState
    ) {
        _state.update { current ->
            current.copy(
                diceList = current.diceList.map {
                    if (it.dice.id == diceId) transform(it)
                    else it
                }
            )
        }
    }
}