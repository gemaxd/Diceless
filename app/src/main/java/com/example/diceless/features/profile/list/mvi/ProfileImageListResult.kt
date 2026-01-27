package com.example.diceless.features.profile.list.mvi

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard

sealed class ProfileImageListResult {
    data class OnImageSelect(val playerData: PlayerData, val backgroundProfile: BackgroundProfileData) : ProfileImageListResult()
}