package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.model.PlayerData
import kotlin.String

@Entity(tableName = "game_scheme")
data class GameSchemeEntity(
    @PrimaryKey val schemeName: String,
    val gameScheme: SchemeEnum
)

fun GameSchemeEntity?.toDomain() : GameSchemeData? {
    return this?.let {
        GameSchemeData(
            schemeName = this.schemeName,
            schemeEnum = this.gameScheme
        )
    }
}