package com.manarimjesse.diceless.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manarimjesse.diceless.data.datasource.local.dao.BackgroundProfileDao
import com.manarimjesse.diceless.data.datasource.local.dao.MatchDao
import com.manarimjesse.diceless.data.datasource.local.dao.MatchHistoryDao
import com.manarimjesse.diceless.data.datasource.local.dao.PlayerDao
import com.manarimjesse.diceless.data.datasource.local.entity.BackgroundProfileEntity
import com.manarimjesse.diceless.data.datasource.local.entity.MatchDataEntity
import com.manarimjesse.diceless.data.datasource.local.entity.MatchHistoryEntity
import com.manarimjesse.diceless.data.datasource.local.entity.PlayerEntity
import com.manarimjesse.diceless.data.datasource.local.entity.typeconverters.MatchDataConverters
import com.manarimjesse.diceless.data.datasource.local.entity.typeconverters.MatchHistoryConverters
import com.manarimjesse.diceless.data.datasource.local.entity.typeconverters.PlayerConverters

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