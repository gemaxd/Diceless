package com.example.diceless.ui.battlegrid.mvi

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.PlayerData

data class BattleGridState(
    val players: List<PlayerData> = emptyList(),
    var selectedScheme: SchemeEnum = SchemeEnum.QUADRA_STANDARD,
    val selectedStartingLife: Int = 40,
    val allowSelfCommanderDamage: Boolean = false,
    val linkCommanderDamageToLife: Boolean = false,
    var showMonarchSymbol: Boolean = false
)
