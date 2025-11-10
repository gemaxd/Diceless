package com.example.diceless.features.cardsearch.mvi

sealed class CardSearchActions {
    data class OnSearchQueryChanged(val query: String) : CardSearchActions()
}