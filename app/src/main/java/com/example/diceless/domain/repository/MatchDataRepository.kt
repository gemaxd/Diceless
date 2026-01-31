package com.example.diceless.domain.repository

import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.domain.model.MatchData

interface MatchDataRepository {
    suspend fun fetchCurrentOpenMatch(): MatchData?
    suspend fun registerMatchData(matchDataEntity: MatchDataEntity): Long
    suspend fun updateMatchDataPlayerQuantity(playerQuantity: Int, matchId: Long)
    suspend fun endCurrentMatch(finishedAt: Long, matchId: Long)
}
