package com.example.diceless.domain.model

import com.example.diceless.data.entity.MatchDataEntity

data class MatchData(
    val id: Long = 0,
    val playersCount: Int = 1,
    val createdAt: Long = System.currentTimeMillis(),
)

fun MatchData.toEntity(): MatchDataEntity =
    MatchDataEntity(
        createdAt = createdAt,
        playersCount = playersCount,
    )