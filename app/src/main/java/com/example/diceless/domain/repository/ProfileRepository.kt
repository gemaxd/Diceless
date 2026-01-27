package com.example.diceless.domain.repository

import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getAllImageProfiles(): List<BackgroundProfileData>
}