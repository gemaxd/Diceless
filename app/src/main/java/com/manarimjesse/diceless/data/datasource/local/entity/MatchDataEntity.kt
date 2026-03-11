package com.manarimjesse.diceless.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manarimjesse.diceless.data.datasource.local.entity.typeconverters.AppJson
import com.manarimjesse.diceless.domain.model.MatchData

@Entity(tableName = "match_data")
data class MatchDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val createdAt: Long,
    val finishedAt: Long? = null,
    val durationMillis: Long? = null,
    val players: String,
    val startingLife: Int,
    val gameScheme: String
)

fun MatchDataEntity.toDomain(): MatchData =
    MatchData(
        id = id,
        createdAt = createdAt,
        finishedAt = finishedAt,
        players = AppJson.decodeFromString(players),
        startingLife = startingLife,
        gameScheme = AppJson.decodeFromString(gameScheme),
        durationMillis = durationMillis
    )
