package com.example.diceless.domain.usecase

import com.example.diceless.data.repository.PlayerProfileRepository
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard
import javax.inject.Inject

class PlayerProfileUseCase @Inject constructor(
    private val repository: PlayerProfileRepository
) {
    suspend operator fun invoke(player: PlayerData, backgroundProfileId: BackgroundProfileData? = null) =
        repository.savePlayer(player = player, backgroundProfileId = backgroundProfileId)
    }
