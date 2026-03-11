package com.manarimjesse.diceless.features.bottombelt.options.diceroll.mvi

import com.manarimjesse.diceless.domain.model.DiceType

data class DiceRollState(
    val diceList: List<DiceUIState> = emptyList()
)

data class DiceUIState(
    val dice: DiceType,
    val rolling: Boolean = false,
    val result: String? = null
)
