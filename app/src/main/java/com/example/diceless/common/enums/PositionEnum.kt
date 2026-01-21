package com.example.diceless.common.enums


enum class PositionEnum(val pos: Int) {
    PLAYER_ONE(pos = 1),
    PLAYER_TWO(pos = 2),
    PLAYER_THREE(pos = 3),
    PLAYER_FOUR(pos = 4);

    companion object {
        fun getPosition(pos: Int): PositionEnum {
            return entries.firstOrNull { it.pos == pos } ?: PLAYER_ONE
        }
    }

}