package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.data.entity.relation.MatchWithHistoryChangesEntity
import com.example.diceless.domain.HistoryPlayerBasicData
import com.example.diceless.domain.model.MatchData
import kotlinx.coroutines.flow.Flow

@Dao
abstract interface MatchDao {

    @Query("SELECT * FROM match_data")
    fun getAllHistories(): Flow<List<MatchWithHistoryChangesEntity>>

    @Insert
    suspend fun insertMatch(match: MatchDataEntity): Long

    @Query("SELECT * FROM match_data WHERE finishedAt IS NULL LIMIT 1")
    suspend fun fetchCurrentMatch(): MatchData?

    @Query("UPDATE match_data SET players = :players WHERE id = :matchId")
    suspend fun updateMatchPlayerQuantity(players: String, matchId: Long)

    @Query("UPDATE match_data SET finishedAt = :finishedAt WHERE id = :matchId")
    suspend fun endCurrentMatch(finishedAt: Long, matchId: Long)
}