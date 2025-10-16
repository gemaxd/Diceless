package com.example.diceless.domain.model

import com.example.diceless.common.enums.PositionEnum
import java.util.UUID

data class PlayerData(
    val playerPosition: PositionEnum,
    val name: String,
    var life: Int = 40,
    var counters: List<CounterData> = getDefaultCounterData(),
    var commanderDamageReceived: MutableList<CommanderDamage> = mutableListOf()
) {
    fun getCurrentLifeWithCommanderDamage() =
        life - commanderDamageReceived.sumOf { it.damage }

}