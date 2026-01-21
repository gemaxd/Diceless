package com.example.diceless.domain.model

import com.example.diceless.data.entity.BackgroundProfileEntity
import kotlinx.serialization.Serializable

@Serializable
data class BackgroundProfileData(
    val cardName: String? = null,
    val imageUri: String? = null,
    val backgroundColor: String = "#BBB"
)

fun BackgroundProfileData.toEntity() = BackgroundProfileEntity(
    id = cardName ?: "NONE",
    cardName = cardName,
    imageUri = imageUri,
    backgroundColor = backgroundColor
)
