package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diceless.data.entity.MatchHistoryEntity
import com.example.diceless.domain.model.MatchData

@Dao
abstract interface MatchHistoryDao {

    @Insert
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)

    @Query("SELECT * FROM match_history WHERE matchId = :matchId")
    suspend fun fetchMatchHistories(matchId: Long): List<MatchHistoryEntity>
}