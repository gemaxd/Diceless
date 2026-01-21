package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.domain.model.PlayerData
import kotlin.String

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val playerPosition: PositionEnum,
    val backgroundProfileId: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

fun PlayerEntity.toDomain() = PlayerData(
     name = name,
     playerPosition = playerPosition
)