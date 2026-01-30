package com.example.diceless.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diceless.data.dao.BackgroundProfileDao
import com.example.diceless.data.dao.GameSchemeDao
import com.example.diceless.data.dao.MatchDao
import com.example.diceless.data.dao.PlayerDao
import com.example.diceless.data.entity.BackgroundProfileEntity
import com.example.diceless.data.entity.GameSchemeEntity
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.data.entity.MatchHistoryEntity
import com.example.diceless.data.entity.PlayerEntity
import com.example.diceless.data.entity.typeconverters.PlayerConverters

@Database(
    entities = [
        PlayerEntity::class,
        BackgroundProfileEntity::class,
        GameSchemeEntity::class,
        MatchDataEntity::class,
        MatchHistoryEntity::class
    ],
    version = 1,           // comece com 1; incremente quando mudar schema
    exportSchema = false   // pode ativar depois se quiser schema exportado
)

@TypeConverters(
    PlayerConverters::class
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun backgroundDao(): BackgroundProfileDao
    abstract fun gameSchemeDao(): GameSchemeDao
    abstract fun matchDao(): MatchDao
}