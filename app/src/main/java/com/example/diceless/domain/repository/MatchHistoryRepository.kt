package com.example.diceless.domain.repository

import com.example.diceless.data.entity.MatchHistoryEntity
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryRegistry

interface MatchHistoryRepository {
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)
    suspend fun fetchMatchHistories(matchDataId: Long): List<MatchHistoryEntity>
}
