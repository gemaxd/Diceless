package com.example.diceless.features.profile.list.mvi

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard

sealed class ProfileImageListActions {
    data object OnProfileImagesLoad: ProfileImageListActions()
    data class OnProfileImageSelected(val playerData: PlayerData, val backgroundProfileData: BackgroundProfileData): ProfileImageListActions()
}