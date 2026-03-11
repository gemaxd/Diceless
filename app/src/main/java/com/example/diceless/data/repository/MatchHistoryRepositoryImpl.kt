package com.example.diceless.data.repository

import com.example.diceless.data.datasource.local.dao.MatchHistoryDao
import com.example.diceless.data.datasource.local.entity.MatchDataEntity
import com.example.diceless.data.datasource.local.entity.MatchHistoryEntity
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class MatchHistoryRepositoryImpl @Inject constructor(
    private val matchHistoryDao: MatchHistoryDao
): MatchHistoryRepository {
    override suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity) {
        matchHistoryDao.insertHistoryChange(historyRegistry = historyRegistry)
    }

    override suspend fun fetchMatchHistories(matchDataId: Long): List<MatchHistoryEntity> {
        return matchHistoryDao.fetchMatchHistories(matchId = matchDataId)
    }

    override suspend fun fetchAllMatchData(): List<MatchDataEntity> {
        return matchHistoryDao.fetchAllMatchData()
    }
}