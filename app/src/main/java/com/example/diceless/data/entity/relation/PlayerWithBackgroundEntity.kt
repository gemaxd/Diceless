package com.example.diceless.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.diceless.data.entity.BackgroundProfileEntity
import com.example.diceless.data.entity.PlayerEntity
import com.example.diceless.data.entity.toDomain
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData

data class PlayerWithBackgroundEntity(
    @Embedded val player: PlayerEntity,

    @Relation(
        parentColumn = "backgroundProfileId",
        entityColumn = "id"
    )
    val background: BackgroundProfileEntity?
)

fun PlayerWithBackgroundEntity.toDomain() = PlayerWithBackgroundData(
    player = player.toDomain(),
    backgroundProfile = background?.toDomain()
)

fun PlayerWithBackgroundEntity.toPlayerData(): PlayerData {
    return player.toDomain().copy(
        backgroundProfile = background?.toDomain()
    )
}