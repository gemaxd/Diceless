package com.manarimjesse.diceless.domain.repository

import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.model.PlayerData
import com.manarimjesse.diceless.domain.model.aggregated.PlayerWithBackgroundData

interface PlayerRepository {
    suspend fun getAllPlayers(): List<PlayerData>
    suspend fun getPlayerWithBackground(playerId: String): PlayerWithBackgroundData?
    suspend fun insertPlayerWithBackground(
        player: PlayerData,
        background: BackgroundProfileData?
    )
    suspend fun updatePlayers(players: List<PlayerData>)
    suspend fun updatePlayer(players: PlayerData)
    suspend fun getPlayerSnapShot(): List<PlayerData>
    suspend fun insertPlayers(players: List<PlayerData>)
}