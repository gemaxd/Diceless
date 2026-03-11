package com.manarimjesse.diceless.app.di

import com.manarimjesse.diceless.data.datasource.local.dao.BackgroundProfileDao
import com.manarimjesse.diceless.data.datasource.local.dao.MatchDao
import com.manarimjesse.diceless.data.datasource.local.dao.MatchHistoryDao
import com.manarimjesse.diceless.data.datasource.local.dao.PlayerDao
import com.manarimjesse.diceless.data.datasource.remote.api.ScryfallApi
import com.manarimjesse.diceless.data.repository.MatchDataRepositoryImpl
import com.manarimjesse.diceless.data.repository.MatchHistoryRepositoryImpl
import com.manarimjesse.diceless.data.repository.PlayerProfileRepositoryImpl
import com.manarimjesse.diceless.data.repository.PlayerRepositoryImpl
import com.manarimjesse.diceless.data.repository.ProfileRepositoryImpl
import com.manarimjesse.diceless.data.repository.ScryfallRepositoryImpl
import com.manarimjesse.diceless.domain.repository.MatchDataRepository
import com.manarimjesse.diceless.domain.repository.MatchHistoryRepository
import com.manarimjesse.diceless.domain.repository.PlayerProfileRepository
import com.manarimjesse.diceless.domain.repository.PlayerRepository
import com.manarimjesse.diceless.domain.repository.ProfileRepository
import com.manarimjesse.diceless.domain.repository.ScryfallRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCardRepository(api: ScryfallApi): ScryfallRepository =
        ScryfallRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePlayerProfileRepository(playerDao: PlayerDao): PlayerProfileRepository =
        PlayerProfileRepositoryImpl(playerDao)

    @Provides
    @Singleton
    fun provideProfileRepository(backgroundDao: BackgroundProfileDao): ProfileRepository =
        ProfileRepositoryImpl(backgroundDao)

    @Provides
    @Singleton
    fun providePlayerRepository(
        playerDao: PlayerDao,
        backgroundDao: BackgroundProfileDao
    ): PlayerRepository = PlayerRepositoryImpl(playerDao, backgroundDao)

    @Provides
    @Singleton
    fun provideMatchDataRepository(
        matchDao: MatchDao
    ): MatchDataRepository = MatchDataRepositoryImpl( matchDao = matchDao)

    @Provides
    @Singleton
    fun provideMatchHistoryRepository(
        matchHistoryDao: MatchHistoryDao
    ): MatchHistoryRepository = MatchHistoryRepositoryImpl(matchHistoryDao = matchHistoryDao)

}