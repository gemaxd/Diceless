package com.manarimjesse.diceless.features.profile.list.mvi

import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.model.PlayerData

sealed class ProfileImageListActions {
    data object OnProfileImagesLoad: ProfileImageListActions()
    data class OnProfileImageSelected(val playerData: PlayerData, val backgroundProfileData: BackgroundProfileData): ProfileImageListActions()
}