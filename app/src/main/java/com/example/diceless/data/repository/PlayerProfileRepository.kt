package com.example.diceless.data.repository

import com.example.diceless.data.datasource.local.dao.PlayerDao
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.toEntity
import com.example.diceless.domain.repository.PlayerProfileRepository

class PlayerProfileRepositoryImpl (val playerDao: PlayerDao) : PlayerProfileRepository {

    override suspend fun savePlayer(player: PlayerData, backgroundProfileId: BackgroundProfileData?) {
        val entity = player.toEntity(player.name)
        playerDao.upsertPlayer(entity)
    }

}
