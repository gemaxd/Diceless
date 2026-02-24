package com.example.diceless.data.repository

import androidx.room.Transaction
import com.example.diceless.data.dao.BackgroundProfileDao
import com.example.diceless.data.dao.GameSchemeDao
import com.example.diceless.data.dao.PlayerDao
import com.example.diceless.data.entity.GameSchemeEntity
import com.example.diceless.data.entity.relation.toDomain
import com.example.diceless.data.entity.toDomain
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import com.example.diceless.domain.model.toEntity
import com.example.diceless.domain.repository.GameSchemeRepository
import com.example.diceless.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameSchemeRepositoryImpl(
    private val gameSchemeDao: GameSchemeDao
) : GameSchemeRepository {

    override suspend fun getGameScheme(): GameSchemeData? {
        return gameSchemeDao
            .getGameScheme().toDomain()
    }

    override suspend fun saveGameScheme(gameSchemeData: GameSchemeData) {
        gameSchemeDao.deleteAllFromScheme()
        gameSchemeDao.upsertGameScheme(gameSchemeData.toEntity())
    }
}