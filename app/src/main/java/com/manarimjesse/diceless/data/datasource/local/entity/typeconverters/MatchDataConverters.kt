package com.manarimjesse.diceless.data.datasource.local.entity.typeconverters

import androidx.room.TypeConverter
import com.manarimjesse.diceless.domain.model.PlayerData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MatchDataConverters {
    @TypeConverter
    fun fromSource(source: List<PlayerData>): String = Json.encodeToString(source)

    @TypeConverter
    fun toSource(value: String): List<PlayerData> =
        Json.decodeFromString(value)


}