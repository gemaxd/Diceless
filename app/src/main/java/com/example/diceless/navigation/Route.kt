package com.example.diceless.navigation

import androidx.navigation3.runtime.NavKey
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object BattleGrid : Route, NavKey

    @Serializable
    data class CardSearch(
        val playerData: PlayerData,
        val onCardSelected: (ScryfallCard) -> Unit
    ) : Route, NavKey

}