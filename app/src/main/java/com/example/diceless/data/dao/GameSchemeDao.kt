package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.diceless.data.entity.GameSchemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameSchemeDao {

    @Query("DELETE FROM game_scheme")
    suspend fun deleteAllFromScheme()

    @Upsert
    suspend fun upsertGameScheme(scheme: GameSchemeEntity)

    @Query("SELECT * FROM game_scheme LIMIT 1")
    fun getGameScheme(): Flow<GameSchemeEntity?>
}