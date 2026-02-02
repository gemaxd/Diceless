package com.example.diceless.data.entity.typeconverters

import androidx.room.TypeConverter
import com.example.diceless.domain.HistoryPlayerBasicData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MatchDataConverters {

    @TypeConverter
    fun fromSource(source: List<HistoryPlayerBasicData>): String = Json.encodeToString(source)

    @TypeConverter
    fun toSource(value: String): List<HistoryPlayerBasicData> =
        Json.decodeFromString(value)
}