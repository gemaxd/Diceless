package com.manarimjesse.diceless.features.bottombelt.options.diceroll.mvi

sealed class DiceRollActions {
    data class OnDiceClicked(val diceId: String) : DiceRollActions()
}
