package com.example.diceless.domain.model

data class DiceType(
    val id: String,
    val label: String,
    val sides: Int? = null, // null = coin,
    val diceRes: Int? = null
)