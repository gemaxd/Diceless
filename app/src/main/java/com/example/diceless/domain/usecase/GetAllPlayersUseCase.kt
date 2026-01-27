package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import com.example.diceless.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlayersUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    operator fun invoke(): Flow<List<PlayerData>> =
        repository.getAllPlayers()
}