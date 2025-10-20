package com.example.diceless.ui.battlegrid.mvi

import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.PlayerData

sealed class BattleGridActions {
    data class OnLifeIncreased(val player: PlayerData) : BattleGridActions()
    data class OnLifeDecreased(val player: PlayerData) : BattleGridActions()
    data class OnCounterToggled(val player: PlayerData, val counter: CounterData) : BattleGridActions()
    data class OnCommanderDamageChanged(
        val receivingPlayer: PlayerData, // Quem está recebendo o dano
        val playerName: String,     // O ID de quem causou o dano
        val amount: Int                  // A quantidade (+1 ou -1)
    ) : BattleGridActions()
    data object OnRestart: BattleGridActions()
}