package com.example.diceless.features.profile.list.mvi

import com.example.diceless.domain.model.BackgroundProfileData

data class ProfileImageListState(
    val profiles: List<BackgroundProfileData> = emptyList()
)

sealed class ProfileListState {
    object Idle : ProfileListState()
    object Loading : ProfileListState()
    data class Success(val cards: List<BackgroundProfileData>) : ProfileListState()
    data class Error(val message: String) : ProfileListState()
}