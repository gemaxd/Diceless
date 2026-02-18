package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.data.entity.MatchHistoryEntity

@Dao
abstract interface MatchHistoryDao {

    @Insert
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)

    @Query("SELECT * FROM match_history WHERE matchId = :matchId")
    suspend fun fetchMatchHistories(matchId: Long): List<MatchHistoryEntity>

    @Query("SELECT * FROM match_data")
    suspend fun fetchAllMatchData(): List<MatchDataEntity>
}
