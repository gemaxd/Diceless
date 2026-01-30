package com.example.diceless.domain.model

import com.example.diceless.data.entity.MatchHistoryEntity

data class MatchHistoryRegistry(
    val id: Long = 0L,
    val matchId: Long,
    val playerId: String,
    val delta: Int,
    val lifeBefore: Int,
    val lifeAfter: Int,
    val timestamp: Long,
    val source: MatchHistoryChangeSource,
    val commanderSourcePlayerId: String? = null
)

enum class MatchHistoryChangeSource {
    DAMAGE,
    HEAL,
    COMMANDER_DAMAGE,
    POISON,
    MONARCH,
    OTHER
}

fun MatchHistoryRegistry.toEntity() = MatchHistoryEntity(
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

