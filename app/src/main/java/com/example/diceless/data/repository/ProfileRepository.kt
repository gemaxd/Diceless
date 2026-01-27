package com.example.diceless.data.repository

import com.example.diceless.data.dao.BackgroundProfileDao
import com.example.diceless.data.entity.toDomain
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    val backgroundDao: BackgroundProfileDao
): ProfileRepository {
    override suspend fun getAllImageProfiles(): List<BackgroundProfileData> {
        return backgroundDao
            .getAllProfileImages()
            .map { entity ->
                entity.toDomain()
            }
    }
}