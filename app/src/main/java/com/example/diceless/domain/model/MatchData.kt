package com.example.diceless.domain.model

import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.domain.HistoryPlayerBasicData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class MatchData(
    val id: Long = 0,
    val players: List<HistoryPlayerBasicData> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val finishedAt: Long? = null,
    val startingLife: Int = 40
)

fun MatchData.toEntity(): MatchDataEntity =
    MatchDataEntity(
        createdAt = createdAt,
        finishedAt = finishedAt,
        players = Json.encodeToString(players),
        startingLife = startingLife
    )