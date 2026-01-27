package com.example.diceless.features.profile.search.mvi

import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard

sealed class ProfileImageSearchActions {
    data class OnSearchQueryChanged(val query: String) : ProfileImageSearchActions()
    data class OnImageSelect(val playerData: PlayerData, val backgroundProfile: ScryfallCard) : ProfileImageSearchActions()

    data class OnLoadAllPrints(val printsSearchUri: String): ProfileImageSearchActions()
}