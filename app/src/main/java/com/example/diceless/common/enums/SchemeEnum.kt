package com.example.diceless.common.enums

enum class SchemeEnum(val numbersOfPlayers: Int) {
    SOLO(numbersOfPlayers = 1),
    VERSUS_OPPOSITE(numbersOfPlayers = 2),
    VERSUS_SIDE_BY_SIDE(numbersOfPlayers = 2),
    TRIPLE_STANDARD(numbersOfPlayers = 3),
    QUADRA_STANDARD(numbersOfPlayers = 4),
    QUADRA_VERTICAL(numbersOfPlayers = 4)
}