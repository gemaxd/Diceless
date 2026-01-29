package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.data.entity.relation.MatchWithHistoryChanges
import kotlinx.coroutines.flow.Flow

@Dao
abstract interface MatchDao {

    @Query("SELECT * FROM match_data")
    fun getAllHistories(): Flow<List<MatchWithHistoryChanges>>

    @Insert
    suspend fun insertMatch(match: MatchDataEntity): Long

    @Query("UPDATE match_data SET playersCount = :playerQuantity WHERE id = :matchId")
    suspend fun updateMatchPlayerQuantity(playerQuantity: Int, matchId: Long)
}