package com.example.diceless.core.domain.usecase.bootstrap

import com.example.diceless.core.utils.DefaultGameScheme
import com.example.diceless.core.utils.DefaultPlayers
import com.example.diceless.domain.repository.GameSchemeRepository
import com.example.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class BootstrapAppUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val schemeRepository: GameSchemeRepository

) {

    suspend operator fun invoke() {
        ensurePlayers()
        ensureScheme()
    }

    private suspend fun ensurePlayers() {
        val players = playerRepository.getPlayerSnapShot()

        if (players.isNotEmpty()) return

        val defaultPlayers = DefaultPlayers.create()
        playerRepository.insertPlayers(defaultPlayers)
    }

    private suspend fun ensureScheme(){
        val actualScheme = schemeRepository.getGameScheme()

        if(actualScheme != null) return

        schemeRepository.saveGameScheme(DefaultGameScheme.create())
    }
}