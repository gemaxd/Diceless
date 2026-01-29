package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_data")
data class MatchDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val createdAt: Long, // timestamp (System.currentTimeMillis)
    val finishedAt: Long? = null, // null enquanto a partida estiver ativa
    val durationMillis: Long? = null, // calculado ao finalizar
    val playersCount: Int
)