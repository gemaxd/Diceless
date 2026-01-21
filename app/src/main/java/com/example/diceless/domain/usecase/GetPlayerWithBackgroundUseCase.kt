package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import com.example.diceless.domain.repository.PlayerRepository

class GetPlayerWithBackgroundUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(
        playerId: String
    ): PlayerWithBackgroundData? {
        return repository.getPlayerWithBackground(playerId)
    }
}
