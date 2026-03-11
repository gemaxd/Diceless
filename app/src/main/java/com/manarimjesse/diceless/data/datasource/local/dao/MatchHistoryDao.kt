package com.manarimjesse.diceless.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.manarimjesse.diceless.data.datasource.local.entity.MatchDataEntity
import com.manarimjesse.diceless.data.datasource.local.entity.MatchHistoryEntity

@Dao
interface MatchHistoryDao {

    @Insert
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)

    @Query("SELECT * FROM match_history WHERE matchId = :matchId")
    suspend fun fetchMatchHistories(matchId: Long): List<MatchHistoryEntity>

    @Query("SELECT * FROM match_data")
    suspend fun fetchAllMatchData(): List<MatchDataEntity>
}
