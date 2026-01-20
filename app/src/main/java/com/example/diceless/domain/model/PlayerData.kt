package com.example.diceless.domain.model

import com.example.diceless.common.enums.PositionEnum
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PlayerData(
    val playerPosition: PositionEnum,
    val name: String,
    var life: Int = 40,
    val baseLife: Int = 40,
    var counters: List<CounterData> = getDefaultCounterData(),
    var commanderDamageReceived: MutableList<CommanderDamage> = mutableListOf()
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

// Mapper simples (pode ser extensão ou função)
fun PlayerData.toEntity(id: String, backgroundProfile: ScryfallCard? = null) = PlayerEntity(
    playerPosition = this.playerPosition,
    id = id,
    name = this.name,
    backgroundProfileId = backgroundProfile?.name ?: "01"
)

fun PlayerEntity.toDomain() = PlayerData(
    playerPosition = this.playerPosition,
    name = this.name
    // os outros campos (life, counters, etc.) são inicializados com valores default
)