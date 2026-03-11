package com.manarimjesse.diceless.core.utils

import com.manarimjesse.diceless.domain.model.enums.SchemeEnum
import com.manarimjesse.diceless.domain.model.GameSchemeData

object DefaultGameScheme {
    fun create(): GameSchemeData {
        return GameSchemeData(
            schemeName = SchemeEnum.SOLO.name,
            schemeEnum = SchemeEnum.SOLO
        )
    }
}
