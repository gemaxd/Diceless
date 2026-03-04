package com.example.diceless.domain.match.reducer

import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.PlayerData

sealed interface MatchAction {
    //Busca por partida aberta
    data object OnFetchCurrentMatch: MatchAction

    //Registra nova partida
    data object OnRegisterMatch: MatchAction

    //Reinicia o jogo
    data object RestartMatch: MatchAction
    data class OnMatchRestarted(val updatedPlayers: List<PlayerData>): MatchAction

    //Scheme
    data class OnChangeScheme(val scheme: SchemeEnum) : MatchAction
    data class MatchUpdated(val updatedMatch: MatchData) : MatchAction

    data class OnBackgroundSelected(val player: PlayerData, val card: BackgroundProfileData) : MatchAction
    data object ToggleMonarchCounter: MatchAction
    data class OnLifeChange(val player: PlayerData, val delta: Int): MatchAction
    data object InitializeMatch : MatchAction
    data object CheckMatchEnd: MatchAction
    data object OnInitialPlayerLoad: MatchAction
    data class InitialPlayersLoaded(
        val players: List<PlayerData>
    ): MatchAction
    data class OnCommanderDamageChanged(
        val receivingPlayer: PlayerData, // Quem está recebendo o dano
        val playerName: String,     // O ID de quem causou o dano
        val amount: Int                  // A quantidade (+1 ou -1)
    ) : MatchAction
    data class OnCounterSelected(
        val player: PlayerData,
        val counter: CounterData
    ) : MatchAction
    data class OnChangeCounterValue(
        val player: PlayerData,
        val counter: CounterData,
        val delta: Int
    ): MatchAction

}