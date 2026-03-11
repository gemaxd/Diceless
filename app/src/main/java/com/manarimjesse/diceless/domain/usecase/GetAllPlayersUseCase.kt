package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.PlayerData
import com.manarimjesse.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class GetAllPlayersUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(): List<PlayerData> =
        repository.getAllPlayers()
}