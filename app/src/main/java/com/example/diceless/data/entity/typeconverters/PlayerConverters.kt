package com.example.diceless.data.entity.typeconverters

import androidx.room.TypeConverter
import com.example.diceless.domain.model.CommanderDamage
import com.example.diceless.domain.model.CounterData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PlayerConverters {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun countersToJson(value: List<CounterData>): String =
        json.encodeToString(value)

    @TypeConverter
    fun jsonToCounters(value: String): List<CounterData> =
        json.decodeFromString(value)

    @TypeConverter
    fun commanderDamageToJson(value: List<CommanderDamage>): String =
        json.encodeToString(value)

    @TypeConverter
    fun jsonToCommanderDamage(value: String): List<CommanderDamage> =
        json.decodeFromString(value)
}