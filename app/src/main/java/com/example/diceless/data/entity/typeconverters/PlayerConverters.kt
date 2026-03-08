package com.example.diceless.data.entity.typeconverters

import androidx.room.TypeConverter
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.CommanderDamage
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.GameSchemeData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val AppJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

class PlayerConverters {

    @TypeConverter
    fun countersToJson(value: List<CounterData>): String =
        AppJson.encodeToString(value)

    @TypeConverter
    fun jsonToCounters(value: String): List<CounterData> =
        AppJson.decodeFromString(value)

    @TypeConverter
    fun commanderDamageToJson(value: List<CommanderDamage>): String =
        AppJson.encodeToString(value)

    @TypeConverter
    fun jsonToCommanderDamage(value: String): List<CommanderDamage> =
        AppJson.decodeFromString(value)

    @TypeConverter
    fun gameSchemeToJson(source: GameSchemeData): String = AppJson.encodeToString(source)

    @TypeConverter
    fun jsonToGameScheme(value: String): GameSchemeData =
        AppJson.decodeFromString(value)

    @TypeConverter
    fun backgroundProfileToJson(source: BackgroundProfileData): String = AppJson.encodeToString(source)

    @TypeConverter
    fun jsonToBackgroundProfile(value: String): BackgroundProfileData =
        AppJson.decodeFromString(value)
}