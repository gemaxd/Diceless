package com.example.diceless.domain.repository

import com.example.diceless.data.entity.MatchDataEntity

interface MatchHistoryRepository {
    suspend fun registerMatchData(matchDataEntity: MatchDataEntity): Long
    suspend fun updateMatchDataPlayerQuantity(playerQuantity: Int, matchId: Long)
}