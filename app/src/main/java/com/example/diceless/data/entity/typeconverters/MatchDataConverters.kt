package com.example.diceless.data.entity.typeconverters

import androidx.room.TypeConverter
import com.example.diceless.domain.HistoryPlayerBasicData
import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.model.PlayerData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MatchDataConverters {
    @TypeConverter
    fun fromSource(source: List<PlayerData>): String = Json.encodeToString(source)

    @TypeConverter
    fun toSource(value: String): List<PlayerData> =
        Json.decodeFromString(value)


}