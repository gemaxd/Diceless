package com.manarimjesse.diceless.domain.model.aggregated

import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.model.PlayerData

data class PlayerWithBackgroundData(
    val player: PlayerData,
    val backgroundProfile: BackgroundProfileData?
)