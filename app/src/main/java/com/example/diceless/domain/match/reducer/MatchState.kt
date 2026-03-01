package com.example.diceless.domain.match.reducer

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.PlayerData

data class MatchState(
    val isLoading: Boolean = true,
    val players: List<PlayerData> = emptyList(),
    val scheme: SchemeEnum? = null,
    val winnerId: String? = null,
    val showMonarchSymbol: Boolean = false,
    val matchData: MatchData = MatchData(),
    val allowSelfCommanderDamage: Boolean = false,
    val linkCommanderDamageToLife: Boolean = false,
    val selectedStartingLife: Int = 40,
) {
    companion object {
        fun initial() = MatchState()
    }
}