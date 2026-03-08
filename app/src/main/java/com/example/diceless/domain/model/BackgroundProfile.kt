package com.example.diceless.domain.model

import com.example.diceless.data.entity.BackgroundProfileEntity
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class BackgroundProfileData(
    val id: String = "NONE",
    val cardName: String = "NONE",
    val imageUri: String = "",
    val backgroundColor: Long = 0
)

fun BackgroundProfileData.toEntity() = BackgroundProfileEntity(
    id = id,
    cardName = cardName,
    imageUri = imageUri,
    backgroundColor = backgroundColor
)


fun randomColor(): Long {
    val r = Random.nextLong(80, 220)
    val g = Random.nextLong(80, 220)
    val b = Random.nextLong(80, 220)

    return (0xFF000000 or (r shl 16) or (g shl 8) or b)
}
