package com.example.diceless.ui.battlegrid.mvi

import com.example.diceless.domain.model.PlayerData

sealed class BattleGridActions {
    data class OnLifeIncreased(val player: PlayerData) : BattleGridActions()
    data class OnLifeDecreased(val player: PlayerData) : BattleGridActions()
}