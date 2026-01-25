package com.example.diceless.domain.repository

import com.example.diceless.data.entity.GameSchemeEntity
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import kotlinx.coroutines.flow.Flow

interface GameSchemeRepository {
    suspend fun getGameScheme(): Flow<GameSchemeData?>
    suspend fun saveGameScheme(gameSchemeData: GameSchemeData)
}