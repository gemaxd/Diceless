package com.example.diceless.domain.repository

import com.example.diceless.data.datasource.local.entity.MatchDataEntity
import com.example.diceless.data.datasource.local.entity.MatchHistoryEntity

interface MatchHistoryRepository {
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)
    suspend fun fetchMatchHistories(matchDataId: Long): List<MatchHistoryEntity>
    suspend fun fetchAllMatchData(): List<MatchDataEntity>
}
