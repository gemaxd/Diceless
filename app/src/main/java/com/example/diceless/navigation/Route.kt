package com.example.diceless.navigation

import androidx.navigation3.runtime.NavKey
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object BattleGrid : Route, NavKey

    @Serializable
    data object StartingArea : Route, NavKey

    @Serializable
    data object HistoryArea : Route, NavKey

    @Serializable
    data class HistoryDetail(
        val matchId: Long
    ) : Route, NavKey

    @Serializable
    data class ProfileImage(
        val playerData: PlayerData,
        val onCardSelected: (BackgroundProfileData) -> Unit
    ) : Route, NavKey

    @Serializable
    data class ProfileImageSearch(
        val playerData: PlayerData,
        val onCardSelected: (BackgroundProfileData) -> Unit
    ) : Route, NavKey

    @Serializable
    data class ProfileImageList(
        val playerData: PlayerData,
        val onProfileSelected: (BackgroundProfileData) -> Unit
    ) : Route, NavKey

}