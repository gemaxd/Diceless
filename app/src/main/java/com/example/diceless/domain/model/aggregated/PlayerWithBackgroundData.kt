package com.example.diceless.domain.model.aggregated

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData

data class PlayerWithBackgroundData(
    val player: PlayerData,
    val backgroundProfile: BackgroundProfileData?
)