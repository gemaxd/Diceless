package com.example.diceless.features.battlegrid.mvi

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.PlayerData

data class BattleGridState(
    val activePlayers: List<PlayerData> = emptyList(),
    val totalPlayers: List<PlayerData> = emptyList(),
    var selectedScheme: SchemeEnum = SchemeEnum.SOLO,
    val selectedStartingLife: Int = 40,
    val matchData: MatchData = MatchData(),
    val allowSelfCommanderDamage: Boolean = false,
    val linkCommanderDamageToLife: Boolean = false,
    var showMonarchSymbol: Boolean = false
)
