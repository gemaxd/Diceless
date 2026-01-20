package com.example.diceless.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diceless.data.dao.PlayerDao
import com.example.diceless.domain.model.BackgroundProfileEntity
import com.example.diceless.domain.model.PlayerEntity

@Database(
    entities = [
        PlayerEntity::class,
        BackgroundProfileEntity::class
    ],
    version = 1,           // comece com 1; incremente quando mudar schema
    exportSchema = false   // pode ativar depois se quiser schema exportado
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    // adicione outros DAOs conforme precisar
}