package com.manarimjesse.diceless.domain

import com.manarimjesse.diceless.domain.model.enums.PositionEnum
import kotlinx.serialization.Serializable

@Serializable
data class HistoryPlayerBasicData(
    val playerPosition: PositionEnum = PositionEnum.PLAYER_ONE,
    val name: String,
    val backgroundImageUri: String,
    val isWinner: Boolean = false
)
