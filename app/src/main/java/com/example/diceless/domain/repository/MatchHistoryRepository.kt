package com.example.diceless.domain.repository

import com.example.diceless.data.entity.MatchHistoryEntity

interface MatchHistoryRepository {
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)
}
