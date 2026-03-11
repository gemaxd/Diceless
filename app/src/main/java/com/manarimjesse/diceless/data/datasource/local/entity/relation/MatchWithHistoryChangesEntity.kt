package com.manarimjesse.diceless.data.datasource.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.manarimjesse.diceless.data.datasource.local.entity.MatchDataEntity
import com.manarimjesse.diceless.data.datasource.local.entity.MatchHistoryEntity

data class MatchWithHistoryChangesEntity(
    @Embedded val match: MatchDataEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "matchId"
    )
    val registries: List<MatchHistoryEntity>
)