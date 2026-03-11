package com.example.diceless.data.datasource.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.diceless.data.datasource.local.entity.BackgroundProfileEntity
import com.example.diceless.data.datasource.local.entity.PlayerEntity
import com.example.diceless.data.datasource.local.entity.toDomain
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