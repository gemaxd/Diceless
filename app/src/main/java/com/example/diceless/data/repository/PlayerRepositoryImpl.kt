package com.example.diceless.data.repository

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
import kotlin.collections.map

class PlayerRepositoryImpl(
    private val playerDao: PlayerDao,
    private val backgroundDao: BackgroundProfileDao
) : PlayerRepository {
    override suspend fun getAllPlayers(): List<PlayerData> {
        return playerDao
            .getAllPlayers()
            .map { entity ->
                    entity.player.toDomain().copy(
                        backgroundProfile = entity.background?.toDomain()
                    )
            }
    }

    override suspend fun getPlayerWithBackground(
        playerId: String
    ): PlayerWithBackgroundData? {
        return playerDao
            .getPlayerWithBackground(playerId)
            ?.toDomain()
    }

    override suspend fun insertPlayerWithBackground(
        player: PlayerData,
        background: BackgroundProfileData?
    ) {
        background?.let {
            backgroundDao.upsertBackground(it.toEntity())
        }

        playerDao.upsertPlayer(
            player.toEntity(
                id = player.playerPosition.name
            )
        )
    }

    override suspend fun updatePlayers(players: List<PlayerData>) {
        playerDao.upsertPlayers(
            players = players.map { it.toEntity(it.playerPosition.name) }
        )
    }

    override suspend fun updatePlayer(player: PlayerData) {
        playerDao.upsertPlayer(
            player = player.toEntity(player.playerPosition.name)
        )
    }

    override suspend fun getPlayerSnapShot(): List<PlayerData> {
        return playerDao.getPlayersSnapshot().map { playerEntity -> playerEntity.toDomain() }
    }

    override suspend fun insertPlayers(players: List<PlayerData>) {
        playerDao.insertPlayers(players = players.map { playerData -> playerData.toEntity(playerData.playerPosition.name) })
    }


}