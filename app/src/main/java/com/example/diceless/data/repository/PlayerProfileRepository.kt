package com.example.diceless.data.repository

import com.example.diceless.data.dao.PlayerDao
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.model.toEntity
import java.util.UUID

interface PlayerProfileRepository {
    suspend fun savePlayer(player: PlayerData, backgroundProfileId: BackgroundProfileData? = null)
}

class PlayerProfileRepositoryImpl (val playerDao: PlayerDao) : PlayerProfileRepository {

    override suspend fun savePlayer(player: PlayerData, backgroundProfileId: BackgroundProfileData?) {
        val entity = player.toEntity(player.name)
        playerDao.upsertPlayer(entity)
    }

}
