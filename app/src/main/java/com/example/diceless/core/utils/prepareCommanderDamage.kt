package com.example.diceless.core.utils

import com.example.diceless.domain.model.CommanderDamage
import com.example.diceless.domain.model.PlayerData

fun prepareCommanderDamage(
    player: PlayerData,
    players: List<PlayerData>,
    numberOfPlayers: Int
    ): MutableList<CommanderDamage> {

        val opponents =
            players.take(numberOfPlayers).filter { it.name != player.name }

        return opponents.map {
            CommanderDamage(name = it.name, damage = 0)
        }.toMutableList()
    }