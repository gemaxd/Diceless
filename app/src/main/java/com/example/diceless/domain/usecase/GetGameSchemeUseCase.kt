package com.example.diceless.domain.usecase

import com.example.diceless.data.entity.GameSchemeEntity
import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import com.example.diceless.domain.repository.GameSchemeRepository
import com.example.diceless.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameSchemeUseCase @Inject constructor(
    private val repository: GameSchemeRepository
) {
    suspend operator fun invoke(): Flow<GameSchemeData?> =
        repository.getGameScheme()
}