package com.example.diceless.core.utils

import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.domain.model.PlayerData

object DefaultPlayers {
    fun create(): List<PlayerData> {
        val rawList = (1..4).map { index ->
            PlayerData(
                name = "Player $index",
                playerPosition = PositionEnum.getPosition(index)
            )
        }

        val players = rawList.map {
            it.commanderDamageReceived = prepareCommanderDamage(
                player = it,
                players = rawList,
                numberOfPlayers = rawList.size
            )
            it
        }
        return players
    }
}
