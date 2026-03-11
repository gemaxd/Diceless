package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.PlayerData
import com.manarimjesse.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class UpdatePlayersUseCase @Inject constructor(
    private val repository: PlayerRepository
){
    suspend operator fun invoke(players: List<PlayerData>) {
        repository.updatePlayers(players = players)
    }
}