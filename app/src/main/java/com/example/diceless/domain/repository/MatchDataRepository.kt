package com.example.diceless.domain.repository

import com.example.diceless.data.datasource.local.entity.MatchDataEntity
import com.example.diceless.domain.model.MatchData

interface MatchDataRepository {
    suspend fun fetchCurrentOpenMatch(): MatchData?
    suspend fun fetchMatchById(matchId: Long): MatchData?
    suspend fun registerMatchData(matchDataEntity: MatchDataEntity): Long
    suspend fun updateMatchData(matchDataEntity: MatchDataEntity)
    suspend fun endCurrentMatch(finishedAt: Long, matchId: Long)
}
