package com.manarimjesse.diceless.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.manarimjesse.diceless.data.datasource.local.entity.BackgroundProfileEntity

@Dao
interface BackgroundProfileDao {
    @Upsert
    suspend fun upsertBackground(background: BackgroundProfileEntity)

    @Query("SELECT * FROM background_profiles")
    suspend fun getAllProfileImages(): List<BackgroundProfileEntity>
}