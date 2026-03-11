package com.manarimjesse.diceless.data.repository

import com.manarimjesse.diceless.data.datasource.local.dao.BackgroundProfileDao
import com.manarimjesse.diceless.data.datasource.local.entity.toDomain
import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.repository.ProfileRepository

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