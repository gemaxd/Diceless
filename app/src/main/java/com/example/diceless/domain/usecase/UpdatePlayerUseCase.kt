package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class UpdatePlayerUseCase @Inject constructor(
    private val repository: PlayerRepository
){
    suspend operator fun invoke(playerData: PlayerData) {
        repository.updatePlayer(player = playerData)
    }
}