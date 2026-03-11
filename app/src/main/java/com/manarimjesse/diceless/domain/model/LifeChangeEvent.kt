package com.manarimjesse.diceless.domain.model

data class LifeChangeEvent(
    val player: PlayerData,
    val delta: Int
)

data class PendingLifeChange(
    val totalDelta: Int,
    val lifeBefore: Int,
    val lifeAfter: Int
)