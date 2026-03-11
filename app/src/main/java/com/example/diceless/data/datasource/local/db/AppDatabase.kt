package com.example.diceless.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diceless.data.datasource.local.dao.BackgroundProfileDao
import com.example.diceless.data.datasource.local.dao.MatchDao
import com.example.diceless.data.datasource.local.dao.MatchHistoryDao
import com.example.diceless.data.datasource.local.dao.PlayerDao
import com.example.diceless.data.datasource.local.entity.BackgroundProfileEntity
import com.example.diceless.data.datasource.local.entity.MatchDataEntity
import com.example.diceless.data.datasource.local.entity.MatchHistoryEntity
import com.example.diceless.data.datasource.local.entity.PlayerEntity
import com.example.diceless.data.datasource.local.entity.typeconverters.MatchDataConverters
import com.example.diceless.data.datasource.local.entity.typeconverters.MatchHistoryConverters
import com.example.diceless.data.datasource.local.entity.typeconverters.PlayerConverters

@Database(
    entities = [
        PlayerEntity::class,
        BackgroundProfileEntity::class,
        MatchDataEntity::class,
        MatchHistoryEntity::class
    ],
    version = 2,           // comece com 1; incremente quando mudar schema
    exportSchema = false   // pode ativar depois se quiser schema exportado
)

@TypeConverters(
    PlayerConverters::class,
    MatchHistoryConverters::class,
    MatchDataConverters::class
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun backgroundDao(): BackgroundProfileDao
    abstract fun matchDao(): MatchDao
    abstract fun matchHistoryDao(): MatchHistoryDao
}