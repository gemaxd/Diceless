package com.example.diceless.core.domain.usecase.bootstrap

import com.example.diceless.core.utils.DefaultGameScheme
import com.example.diceless.core.utils.DefaultPlayers
import com.example.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class BootstrapAppUseCase @Inject constructor(
    private val playerRepository: PlayerRepository

) {
    suspend operator fun invoke() {
        ensurePlayers()
    }

    private suspend fun ensurePlayers() {
        val players = playerRepository.getPlayerSnapShot()

        if (players.isNotEmpty()) return

        val defaultPlayers = DefaultPlayers.create()
        playerRepository.insertPlayers(defaultPlayers)
    }
}