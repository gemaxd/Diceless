package com.manarimjesse.diceless.domain.repository

import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.model.PlayerData

interface PlayerProfileRepository {
    suspend fun savePlayer(player: PlayerData, backgroundProfileId: BackgroundProfileData? = null)
}