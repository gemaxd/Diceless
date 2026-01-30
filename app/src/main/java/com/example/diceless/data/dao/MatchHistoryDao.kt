package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.diceless.data.entity.MatchHistoryEntity

@Dao
abstract interface MatchHistoryDao {

    @Insert
    suspend fun insertHistoryChange(historyRegistry: MatchHistoryEntity)
}