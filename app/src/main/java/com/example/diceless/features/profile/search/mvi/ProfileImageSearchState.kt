package com.example.diceless.features.profile.search.mvi

import com.example.diceless.domain.model.ScryfallCard

data class ProfileImageSearchState(
    val searchQuery: String = ""
)

sealed class ProfileImageSearchListState {
    object Idle : ProfileImageSearchListState()
    object Loading : ProfileImageSearchListState()
    data class Success(val cards: List<ScryfallCard>) : ProfileImageSearchListState()
    data class Error(val message: String) : ProfileImageSearchListState()
}