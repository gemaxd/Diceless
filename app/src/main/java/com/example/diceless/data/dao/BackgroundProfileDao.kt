package com.example.diceless.data.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.diceless.data.entity.BackgroundProfileEntity

@Dao
interface BackgroundProfileDao {
    @Upsert
    suspend fun upsertBackground(background: BackgroundProfileEntity)
}