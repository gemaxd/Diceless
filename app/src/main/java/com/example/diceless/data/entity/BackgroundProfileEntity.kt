package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diceless.domain.model.BackgroundProfileData

@Entity(tableName = "background_profiles")
data class BackgroundProfileEntity(
    @PrimaryKey
    val id: String,
    val cardName: String?,
    val imageUri: String?,                    // url da imagem (ou path local)
    val backgroundColor: String
)

fun BackgroundProfileEntity.toDomain() = BackgroundProfileData(
    cardName = cardName,
    imageUri = imageUri,
    backgroundColor = backgroundColor
)