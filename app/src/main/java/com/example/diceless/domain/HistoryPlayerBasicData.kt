package com.example.diceless.domain

import com.example.diceless.common.enums.PositionEnum
import kotlinx.serialization.Serializable

@Serializable
data class HistoryPlayerBasicData(
    val playerPosition: PositionEnum = PositionEnum.PLAYER_ONE,
    val name: String,
    val backgroundImageUri: String,
    val isWinner: Boolean = false
)
