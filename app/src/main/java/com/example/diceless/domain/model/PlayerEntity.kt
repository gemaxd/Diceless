package com.example.diceless.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diceless.common.enums.PositionEnum

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: String,               // ex: "player_1", UUID, índice + nome
    val name: String,
    val playerPosition: PositionEnum,
    val backgroundProfileId: String? = null,  // chave estrangeira para BackgroundProfile
    val createdAt: Long = System.currentTimeMillis()
)