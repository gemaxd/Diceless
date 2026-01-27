package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.diceless.data.entity.BackgroundProfileEntity
import com.example.diceless.data.entity.relation.PlayerWithBackgroundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BackgroundProfileDao {
    @Upsert
    suspend fun upsertBackground(background: BackgroundProfileEntity)

    @Query("SELECT * FROM background_profiles")
    suspend fun getAllProfileImages(): List<BackgroundProfileEntity>
}