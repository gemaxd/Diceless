package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class InsertPlayerWithBackgroundUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(
        player: PlayerData,
        background: BackgroundProfileData?
    ) {
        repository.insertPlayerWithBackground(player, background)
    }
}
