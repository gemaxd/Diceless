package com.manarimjesse.diceless.domain.model

import com.manarimjesse.diceless.domain.model.enums.SchemeEnum
import kotlinx.serialization.Serializable

@Serializable
data class GameSchemeData(
    val schemeName: String = SchemeEnum.SOLO.name,
    val schemeEnum: SchemeEnum = SchemeEnum.SOLO
)
