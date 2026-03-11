package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.model.PlayerData
import com.manarimjesse.diceless.domain.repository.PlayerRepository
import javax.inject.Inject

class InsertPlayerWithBackgroundUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(
        player: PlayerData,
        background: BackgroundProfileData
    ) {
        repository.insertPlayerWithBackground(player.copy(backgroundProfile = background), background)
    }
}
