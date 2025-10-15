package com.example.diceless.ui.battlegrid.mvi

import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.PlayerData

sealed class BattleGridActions {
    data class OnLifeIncreased(val player: PlayerData) : BattleGridActions()
    data class OnLifeDecreased(val player: PlayerData) : BattleGridActions()
    data class OnCounterToggled(val player: PlayerData, val counter: CounterData) : BattleGridActions()
}