package com.example.diceless.domain.model

import com.example.diceless.domain.model.enums.SchemeEnum
import kotlinx.serialization.Serializable

@Serializable
data class GameSchemeData(
    val schemeName: String = SchemeEnum.SOLO.name,
    val schemeEnum: SchemeEnum = SchemeEnum.SOLO
)
