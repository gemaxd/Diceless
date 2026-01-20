package com.example.diceless.navigation

import com.example.diceless.domain.model.PlayerData

sealed interface NavigationIntent {
    data class OnGoToCardSearch(val playerData: PlayerData) : NavigationIntent
    data class OnGoToLoadProfile(val playerData: PlayerData) : NavigationIntent
}