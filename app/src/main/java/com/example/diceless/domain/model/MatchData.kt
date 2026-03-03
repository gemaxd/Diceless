package com.example.diceless.domain.model

import android.util.Log
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.data.entity.typeconverters.AppJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class MatchData(
    val id: Long = 0,
    val players: List<PlayerData> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val finishedAt: Long? = null,
    val startingLife: Int = 40,
    val gameScheme: GameSchemeData = GameSchemeData()
)

fun MatchData.toEntity(): MatchDataEntity {
    return MatchDataEntity(
        id = id,
        createdAt = createdAt,
        finishedAt = finishedAt,
        players = AppJson.encodeToString(players),
        startingLife = startingLife,
        gameScheme = AppJson.encodeToString(gameScheme)
    )
}

