package com.example.diceless.features.cardsearch.mvi

data class CardSearchState(
    val searchQuery: String = ""
)

sealed class CardImageState {
    object Idle : CardImageState()
    object Loading : CardImageState()
    data class Success(val url: String) : CardImageState()
    data class Error(val message: String) : CardImageState()
}