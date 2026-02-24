package com.example.diceless.domain.repository

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getAllPlayers(): Flow<List<PlayerData>>
    suspend fun getPlayerWithBackground(playerId: String): PlayerWithBackgroundData?
    suspend fun insertPlayerWithBackground(
        player: PlayerData,
        background: BackgroundProfileData?
    )
    suspend fun updatePlayers(players: List<PlayerData>)
    suspend fun updatePlayer(players: PlayerData)
}