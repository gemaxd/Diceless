package com.manarimjesse.diceless.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manarimjesse.diceless.data.datasource.local.entity.MatchDataEntity
import com.manarimjesse.diceless.data.datasource.local.entity.relation.MatchWithHistoryChangesEntity
import com.manarimjesse.diceless.domain.model.MatchData
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Query("SELECT * FROM match_data")
    fun getAllHistories(): Flow<List<MatchWithHistoryChangesEntity>>

    @Insert
    suspend fun insertMatch(match: MatchDataEntity): Long

    @Query("SELECT * FROM match_data WHERE finishedAt IS NULL LIMIT 1")
    suspend fun fetchCurrentMatch(): MatchData?

    @Update
    suspend fun updateMatchData(match: MatchDataEntity)

    @Query("UPDATE match_data SET players = :players WHERE id = :matchId")
    suspend fun updateMatchPlayerQuantity(players: String, matchId: Long)

    @Query("UPDATE match_data SET finishedAt = :finishedAt WHERE id = :matchId")
    suspend fun endCurrentMatch(finishedAt: Long, matchId: Long)

    @Query("SELECT * FROM match_data WHERE id = :matchId LIMIT 1")
    suspend fun fetchMatchById(matchId: Long): MatchData?
}