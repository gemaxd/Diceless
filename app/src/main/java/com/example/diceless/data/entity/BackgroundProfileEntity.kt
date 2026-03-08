package com.example.diceless.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diceless.domain.model.BackgroundProfileData

@Entity(tableName = "background_profiles")
data class BackgroundProfileEntity(
    @PrimaryKey
    val id: String,
    val imageUri: String,
    val cardName: String,
    val backgroundColor: Long
)

fun BackgroundProfileEntity.toDomain() = BackgroundProfileData(
    id = id,
    cardName = cardName,
    imageUri = imageUri,
    backgroundColor = backgroundColor
)