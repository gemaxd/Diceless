package com.manarimjesse.diceless.data.datasource.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.manarimjesse.diceless.data.datasource.local.entity.BackgroundProfileEntity
import com.manarimjesse.diceless.data.datasource.local.entity.PlayerEntity
import com.manarimjesse.diceless.data.datasource.local.entity.toDomain
import com.manarimjesse.diceless.domain.model.aggregated.PlayerWithBackgroundData

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