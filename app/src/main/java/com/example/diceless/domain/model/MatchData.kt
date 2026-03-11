package com.example.diceless.domain.model

import com.example.diceless.data.datasource.local.entity.MatchDataEntity
import com.example.diceless.data.datasource.local.entity.typeconverters.AppJson
import kotlinx.serialization.encodeToString

data class MatchData(
    val id: Long = 0,
    val players: List<PlayerData> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val finishedAt: Long? = null,
    val startingLife: Int = 40,
    val gameScheme: GameSchemeData = GameSchemeData(),
    val durationMillis: Long? = null,
)

fun MatchData.toEntity(): MatchDataEntity {
    return MatchDataEntity(
        id = id,
        createdAt = createdAt,
        finishedAt = finishedAt,
        players = AppJson.encodeToString(players),
        startingLife = startingLife,
        gameScheme = AppJson.encodeToString(gameScheme),
        durationMillis = durationMillis
    )
}

