package com.example.diceless.ui.playergrid.mvi

import com.example.diceless.domain.model.PlayerData


data class BattleGridState(
    val players: List<PlayerData> = emptyList(),

    )
