package com.example.diceless.core.utils

import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.randomColor

object DefaultPlayers {
    fun create(): List<PlayerData> {
        val rawList = (1..4).map { index ->
            val randomColor = randomColor()

            PlayerData(
                name = "Player $index",
                playerPosition = PositionEnum.getPosition(index),
                backgroundProfile = BackgroundProfileData(
                    id = randomColor.toString(),
                    backgroundColor = randomColor
                )
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
