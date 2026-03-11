package com.manarimjesse.diceless.features.profile.list.mvi

import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.model.PlayerData

sealed class ProfileImageListResult {
    data class OnImageSelect(val playerData: PlayerData, val backgroundProfile: BackgroundProfileData) : ProfileImageListResult()
}