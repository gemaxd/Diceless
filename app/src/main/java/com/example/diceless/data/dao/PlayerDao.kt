package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.diceless.data.entity.PlayerEntity
import com.example.diceless.data.entity.relation.PlayerWithBackgroundEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract interface PlayerDao {

    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<PlayerWithBackgroundEntity>>

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayerById(id: String): Flow<PlayerEntity?>

    @Upsert
    suspend fun upsertPlayer(player: PlayerEntity)

    @Query("UPDATE players SET backgroundProfileId = :imageUri WHERE id = :playerId")
    suspend fun updateBackgroundProfileId(playerId: String, imageUri: String?)

    // Se quiser deletar
    @Query("DELETE FROM players WHERE id = :id")
    suspend fun deletePlayer(id: String)

    @Transaction
    @Query("SELECT * FROM players WHERE id = :playerId")
    suspend fun getPlayerWithBackground(
        playerId: String
    ): PlayerWithBackgroundEntity?
}