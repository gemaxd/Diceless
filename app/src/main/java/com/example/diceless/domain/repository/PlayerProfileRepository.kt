package com.example.diceless.domain.repository

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData

interface PlayerProfileRepository {
    suspend fun savePlayer(player: PlayerData, backgroundProfileId: BackgroundProfileData? = null)
}