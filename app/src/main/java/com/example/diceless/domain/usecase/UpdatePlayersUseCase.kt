package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class UpdatePlayersUseCase @Inject constructor(
    private val repository: PlayerRepository
){
    suspend operator fun invoke(players: List<PlayerData>) {
        repository.updatePlayers(players = players)
    }
}