package com.example.diceless.data.datasource.local.entity.typeconverters

import androidx.room.TypeConverter
import com.example.diceless.domain.model.MatchHistoryChangeSource

class MatchHistoryConverters {

    @TypeConverter
    fun fromSource(source: MatchHistoryChangeSource): String = source.name

    @TypeConverter
    fun toSource(value: String): MatchHistoryChangeSource =
        MatchHistoryChangeSource.valueOf(value)
}