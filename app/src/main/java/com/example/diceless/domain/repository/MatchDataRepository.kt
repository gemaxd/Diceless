package com.example.diceless.domain.repository

import com.example.diceless.data.entity.MatchDataEntity

interface MatchDataRepository {
    suspend fun fetchCurrentOpenMatch(): Long?
    suspend fun registerMatchData(matchDataEntity: MatchDataEntity): Long
    suspend fun updateMatchDataPlayerQuantity(playerQuantity: Int, matchId: Long)
    suspend fun endCurrentMatch(finishedAt: Long, matchId: Long)
}
