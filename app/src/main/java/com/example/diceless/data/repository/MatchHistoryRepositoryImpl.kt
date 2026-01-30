package com.example.diceless.data.repository

import com.example.diceless.data.dao.MatchHistoryDao
import com.example.diceless.data.entity.MatchHistoryEntity
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class MatchHistoryRepositoryImpl @Inject constructor(
    private val matchHistoryDao: MatchHistoryDao
): MatchHistoryRepository {
    override suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity) {
        matchHistoryDao.insertHistoryChange(historyRegistry = historyRegistry)
    }
}