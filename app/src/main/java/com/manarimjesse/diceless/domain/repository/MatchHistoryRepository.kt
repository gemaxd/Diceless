package com.manarimjesse.diceless.domain.repository

import com.manarimjesse.diceless.data.datasource.local.entity.MatchDataEntity
import com.manarimjesse.diceless.data.datasource.local.entity.MatchHistoryEntity

interface MatchHistoryRepository {
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)
    suspend fun fetchMatchHistories(matchDataId: Long): List<MatchHistoryEntity>
    suspend fun fetchAllMatchData(): List<MatchDataEntity>
}
