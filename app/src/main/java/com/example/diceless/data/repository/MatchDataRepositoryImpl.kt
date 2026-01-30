package com.example.diceless.data.repository

import com.example.diceless.data.dao.MatchDao
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class MatchDataRepositoryImpl @Inject constructor(
    val matchDao: MatchDao
): MatchDataRepository {
    override suspend fun fetchCurrentOpenMatch(): Long? {
        return matchDao.fetchCurrentMatch()
    }

    override suspend fun registerMatchData(matchDataEntity: MatchDataEntity): Long {
        return matchDao.insertMatch(matchDataEntity)
    }

    override suspend fun updateMatchDataPlayerQuantity(
        playerQuantity: Int,
        matchId: Long
    ) {
        matchDao.updateMatchPlayerQuantity(playerQuantity = playerQuantity, matchId = matchId)
    }

    override suspend fun endCurrentMatch(
        finishedAt: Long,
        matchId: Long
    ) {
        matchDao.endCurrentMatch(finishedAt, matchId)
    }

}