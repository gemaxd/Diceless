package com.example.diceless.features.cardsearch.mvi

import com.example.diceless.data.entity.BackgroundProfileEntity
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard

sealed class CardSearchActions {
    data class OnSearchQueryChanged(val query: String) : CardSearchActions()
    data class OnImageSelect(val playerData: PlayerData, val backgroundProfile: ScryfallCard) : CardSearchActions()
}