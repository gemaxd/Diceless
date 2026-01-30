package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.domain.model.PlayerData
import kotlinx.serialization.json.Json
import kotlin.String

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val life: Int,
    val baseLife: Int,
    val playerPosition: PositionEnum,

    val counters: String, // JSON
    val commanderDamageReceived: String, // JSON

    val backgroundProfileId: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

fun PlayerEntity.toDomain() = PlayerData(
    name = name,
    life = life,
    baseLife = baseLife,
    playerPosition = playerPosition,
    counters = Json.decodeFromString(counters),
    commanderDamageReceived = Json.decodeFromString(commanderDamageReceived),
)