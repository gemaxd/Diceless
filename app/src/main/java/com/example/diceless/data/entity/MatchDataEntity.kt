package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_data")
data class MatchDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val createdAt: Long,
    val finishedAt: Long? = null,
    val durationMillis: Long? = null,
    val players: String,
    val startingLife: Int
)
