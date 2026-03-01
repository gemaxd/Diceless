package com.example.diceless.domain.match.reducer

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.PlayerData

sealed interface MatchAction {
    data object FetchCurrentOpenMatch: MatchAction
    data class OnCurrentMatchFetched(val matchData: MatchData): MatchAction
    data class RegisterMatch(val matchData: MatchData): MatchAction
    data class OnMatchRegistered(val matchData: MatchData): MatchAction
    data object RestartMatch: MatchAction
    data class OnMatchRestarted(val updatedPlayers: List<PlayerData>): MatchAction
    data class ChangeScheme(val scheme: SchemeEnum) : MatchAction
    data class OnBackgroundSelected(val player: PlayerData, val card: BackgroundProfileData) : MatchAction
    data object ToggleMonarchCounter: MatchAction
    data class OnLifeChange(val player: PlayerData, val delta: Int): MatchAction
    data class OnLifeChanged(val player: PlayerData, val delta: Int): MatchAction
    data object InitializeMatch : MatchAction
    data class SchemeLoaded(
        val scheme: SchemeEnum
    ) : MatchAction
    data object CheckMatchEnd: MatchAction
    data object OnInitialPlayerLoad: MatchAction
    data class InitialPlayersLoaded(
        val players: List<PlayerData>
    ): MatchAction
    data class UpdateMatchData(
        val matchData: MatchData
    ): MatchAction
    data class UpdateMatchState(
        val matchData: MatchData
    ): MatchAction
    data class UpdateStartingLife(
        val newValue: Int
    ): MatchAction
}