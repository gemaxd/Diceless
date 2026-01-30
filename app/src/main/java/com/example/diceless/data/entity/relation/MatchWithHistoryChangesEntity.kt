package com.example.diceless.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.diceless.data.entity.MatchDataEntity
import com.example.diceless.data.entity.MatchHistoryEntity

data class MatchWithHistoryChangesEntity(
    @Embedded val match: MatchDataEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "matchId"
    )
    val registries: List<MatchHistoryEntity>
)