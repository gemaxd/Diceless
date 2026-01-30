package com.example.diceless.domain.model

import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.data.entity.PlayerEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.Serial
import java.util.UUID

@Serializable
data class PlayerData(
    val playerPosition: PositionEnum,
    val name: String,
    var life: Int = 40,
    val baseLife: Int = 40,
    var counters: List<CounterData> = getDefaultCounterData(),
    var commanderDamageReceived: List<CommanderDamage> = emptyList(),
    val backgroundProfile: BackgroundProfileData? = BackgroundProfileData()
) {
    fun getCurrentLifeWithCommanderDamage() =
        life - commanderDamageReceived.sumOf { it.damage }

    fun getCorrectLifeValue(
        isDamageLinked: Boolean = false
    ) : Int {
        val currentLife = this.life

        return if (isDamageLinked) {
            getCurrentLifeWithCommanderDamage()
        } else {
            currentLife
        }
    }
}

fun PlayerData.toEntity(id: String) = PlayerEntity(
    life = life,
    baseLife = baseLife,
    id = id,
    playerPosition = playerPosition,
    name = name,
    counters = Json.encodeToString(counters),
    commanderDamageReceived = Json.encodeToString(commanderDamageReceived),
    backgroundProfileId = backgroundProfile?.imageUri
)