package com.example.diceless.domain.repository

import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.data.entity.MatchHistoryEntity

interface MatchHistoryRepository {
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)
    suspend fun fetchMatchHistories(matchDataId: Long): List<MatchHistoryEntity>
    suspend fun fetchAllMatchData(): List<MatchDataEntity>
}
