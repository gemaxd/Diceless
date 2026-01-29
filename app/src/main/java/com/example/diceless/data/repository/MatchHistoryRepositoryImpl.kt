package com.example.diceless.data.repository

import com.example.diceless.data.dao.MatchDao
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class MatchHistoryRepositoryImpl @Inject constructor(
    val matchDao: MatchDao
): MatchHistoryRepository {
    override suspend fun registerMatchData(matchDataEntity: MatchDataEntity): Long {
        return matchDao.insertMatch(matchDataEntity)
    }

    override suspend fun updateMatchDataPlayerQuantity(
        playerQuantity: Int,
        matchId: Long
    ) {
        matchDao.updateMatchPlayerQuantity(playerQuantity = playerQuantity, matchId = matchId)
    }

}