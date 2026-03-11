package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAllProfileImagesUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<List<BackgroundProfileData>> = runCatching {
        repository.getAllImageProfiles()
    }
}