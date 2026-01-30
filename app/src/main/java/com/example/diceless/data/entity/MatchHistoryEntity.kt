package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.diceless.domain.model.MatchHistoryChangeSource
import com.example.diceless.domain.model.MatchHistoryRegistry

@Entity(
    tableName = "match_history",
    foreignKeys = [
        ForeignKey(
            entity = MatchDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["matchId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("matchId"),
        Index("playerId")
    ]
)
data class MatchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val matchId: Long,

    val playerId: String, 
    // pode ser String (UUID) ou Int, depende do seu modelo de Player

    val delta: Int, 
    // +2, -5, etc

    val lifeBefore: Int,
    val lifeAfter: Int,

    val timestamp: Long,

    val source: MatchHistoryChangeSource,
    // enum: DAMAGE, HEAL, COMMANDER_DAMAGE, POISON, MONARCH, ETC

    val commanderSourcePlayerId: String? = null
    // usado se for commander damage
)

fun MatchHistoryEntity.toDomain() = MatchHistoryRegistry(
    id = id,
    matchId = matchId,
    playerId = playerId,
    delta = delta,
    lifeBefore = lifeBefore,
    lifeAfter = lifeAfter,
    timestamp = timestamp,
    source = source,
    commanderSourcePlayerId = commanderSourcePlayerId
)

