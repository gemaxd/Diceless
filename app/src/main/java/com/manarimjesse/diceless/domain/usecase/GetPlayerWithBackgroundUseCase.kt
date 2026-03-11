package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.aggregated.PlayerWithBackgroundData
import com.manarimjesse.diceless.domain.repository.PlayerRepository

class GetPlayerWithBackgroundUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(
        playerId: String
    ): PlayerWithBackgroundData? {
        return repository.getPlayerWithBackground(playerId)
    }
}
