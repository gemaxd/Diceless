package com.manarimjesse.diceless.domain.repository

import com.manarimjesse.diceless.domain.model.BackgroundProfileData

interface ProfileRepository {
    suspend fun getAllImageProfiles(): List<BackgroundProfileData>
}