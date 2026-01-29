package com.example.diceless.data.entity.typeconverters

import androidx.room.TypeConverter
import com.example.diceless.data.entity.MatchHistoryChangeSource

class MatchHistoryConverters {

    @TypeConverter
    fun fromSource(source: MatchHistoryChangeSource): String = source.name

    @TypeConverter
    fun toSource(value: String): MatchHistoryChangeSource =
        MatchHistoryChangeSource.valueOf(value)
}