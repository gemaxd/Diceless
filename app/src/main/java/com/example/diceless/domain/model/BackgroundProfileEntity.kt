package com.example.diceless.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "background_profiles")
data class BackgroundProfileEntity(
    @PrimaryKey val id: String,               // UUID ou gerado
    val cardId: String?,                      // ex: scryfall id
    val cardName: String?,
    val imageUri: String?,                    // url da imagem (ou path local)
    val backgroundColor: String?,             // hex #RRGGBB
    val artist: String?,                      // crédito
    val createdAt: Long = System.currentTimeMillis(),
    val lastUsedAt: Long = System.currentTimeMillis()
)