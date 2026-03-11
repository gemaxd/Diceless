package com.manarimjesse.diceless.data.repository

import com.manarimjesse.diceless.data.datasource.local.dao.PlayerDao
import com.manarimjesse.diceless.domain.model.BackgroundProfileData
import com.manarimjesse.diceless.domain.model.PlayerData
import com.manarimjesse.diceless.domain.model.toEntity
import com.manarimjesse.diceless.domain.repository.PlayerProfileRepository

class PlayerProfileRepositoryImpl (val playerDao: PlayerDao) : PlayerProfileRepository {

    override suspend fun savePlayer(player: PlayerData, backgroundProfileId: BackgroundProfileData?) {
        val entity = player.toEntity(player.name)
        playerDao.upsertPlayer(entity)
    }

}
