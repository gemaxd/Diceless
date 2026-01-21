package com.example.diceless.data.repository

import androidx.room.Transaction
import com.example.diceless.data.dao.BackgroundProfileDao
import com.example.diceless.data.dao.PlayerDao
import com.example.diceless.data.entity.relation.toDomain
import com.example.diceless.data.entity.toDomain
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import com.example.diceless.domain.model.toEntity
import com.example.diceless.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerRepositoryImpl(
    private val playerDao: PlayerDao,
    private val backgroundDao: BackgroundProfileDao
) : PlayerRepository {
    override fun getAllPlayers(): Flow<List<PlayerData>> {
        return playerDao
            .getAllPlayers()
            .map { list ->
                list.map { entity ->
                    entity.player.toDomain().copy(
                        backgroundProfile = entity.background?.toDomain()
                    )
                }
            }
    }

    override suspend fun getPlayerWithBackground(
        playerId: String
    ): PlayerWithBackgroundData? {
        return playerDao
            .getPlayerWithBackground(playerId)
            ?.toDomain()
    }

    @Transaction
    override suspend fun insertPlayerWithBackground(
        player: PlayerData,
        background: BackgroundProfileData?
    ) {
        background?.let {
            backgroundDao.upsertBackground(it.toEntity())
        }

        playerDao.upsertPlayer(
            player.toEntity(
                id = player.playerPosition.name // ou UUID
            )
        )
    }
}