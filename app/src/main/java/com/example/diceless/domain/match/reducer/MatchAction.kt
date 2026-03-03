package com.example.diceless.domain.match.reducer

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.PlayerData

sealed interface MatchAction {
    //Busca por partida aberta
    data object OnFetchCurrentMatch: MatchAction
    data class CurrentMatchFetched(val matchData: MatchData): MatchAction

    //Registra nova partida
    data object OnRegisterMatch: MatchAction
    data class MatchRegistered(val matchData: MatchData): MatchAction

    //Reinicia o jogo
    data object RestartMatch: MatchAction
    data class OnMatchRestarted(val updatedPlayers: List<PlayerData>): MatchAction

    //Scheme
    data class OnChangeScheme(val scheme: SchemeEnum) : MatchAction
    data class SchemeChanged(val updatedMatch: MatchData) : MatchAction
    data class MatchUpdated(val updatedMatch: MatchData) : MatchAction

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