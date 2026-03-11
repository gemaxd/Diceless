package com.manarimjesse.diceless.data.repository

import com.manarimjesse.diceless.data.datasource.local.dao.MatchHistoryDao
import com.manarimjesse.diceless.data.datasource.local.entity.MatchDataEntity
import com.manarimjesse.diceless.data.datasource.local.entity.MatchHistoryEntity
import com.manarimjesse.diceless.domain.repository.MatchHistoryRepository
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