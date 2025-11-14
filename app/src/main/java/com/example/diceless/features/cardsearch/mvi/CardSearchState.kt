package com.example.diceless.features.cardsearch.mvi

import com.example.diceless.domain.model.ScryfallCard

data class CardSearchState(
    val searchQuery: String = ""
)

sealed class CardListState {
    object Idle : CardListState()
    object Loading : CardListState()
    data class Success(val cards: List<ScryfallCard>) : CardListState()
    data class Error(val message: String) : CardListState()
}