package com.example.diceless.domain.model

import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.data.entity.GameSchemeEntity
import com.example.diceless.data.entity.PlayerEntity
import kotlinx.serialization.Serializable
import java.io.Serial
import java.util.UUID

@Serializable
data class GameSchemeData(
    val schemeName: String = SchemeEnum.SOLO.name,
    val schemeEnum: SchemeEnum = SchemeEnum.SOLO
)

fun GameSchemeData.toEntity() = GameSchemeEntity(
    schemeName = this.schemeName,
    gameScheme = this.schemeEnum
)