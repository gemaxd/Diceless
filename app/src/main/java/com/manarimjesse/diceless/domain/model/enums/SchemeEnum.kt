package com.manarimjesse.diceless.domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class SchemeEnum(val numbersOfPlayers: Int) {
    SOLO(numbersOfPlayers = 1),
    VERSUS_OPPOSITE(numbersOfPlayers = 2),
    TRIPLE_STANDARD(numbersOfPlayers = 3),
    QUADRA_STANDARD(numbersOfPlayers = 4)
}