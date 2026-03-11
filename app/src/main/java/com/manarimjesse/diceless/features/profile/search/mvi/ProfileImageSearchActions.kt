package com.manarimjesse.diceless.features.profile.search.mvi

import com.manarimjesse.diceless.domain.model.PlayerData
import com.manarimjesse.diceless.domain.model.ScryfallCard

sealed class ProfileImageSearchActions {
    data class OnSearchQueryChanged(val query: String) : ProfileImageSearchActions()
    data class OnImageSelect(val playerData: PlayerData, val backgroundProfile: ScryfallCard) : ProfileImageSearchActions()

    data class OnLoadAllPrints(val printsSearchUri: String): ProfileImageSearchActions()
}