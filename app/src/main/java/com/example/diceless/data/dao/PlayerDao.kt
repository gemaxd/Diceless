package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.diceless.domain.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract interface PlayerDao {

    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayerById(id: String): Flow<PlayerEntity?>

    @Upsert
    suspend fun upsertPlayer(player: PlayerEntity)

    @Query("UPDATE players SET backgroundProfileId = :backgroundId WHERE id = :playerId")
    suspend fun updateBackgroundProfileId(playerId: String, backgroundId: String?)

    // Se quiser deletar
    @Query("DELETE FROM players WHERE id = :id")
    suspend fun deletePlayer(id: String)
}