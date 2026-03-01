package com.example.diceless.core.utils

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.GameSchemeData

object DefaultGameScheme {
    fun create(): GameSchemeData {
        return GameSchemeData(
            schemeName = SchemeEnum.SOLO.name,
            schemeEnum = SchemeEnum.SOLO
        )
    }
}
