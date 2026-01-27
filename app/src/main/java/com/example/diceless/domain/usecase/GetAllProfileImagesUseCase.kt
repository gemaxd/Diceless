package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAllProfileImagesUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<List<BackgroundProfileData>> = runCatching {
        repository.getAllImageProfiles()
    }
}