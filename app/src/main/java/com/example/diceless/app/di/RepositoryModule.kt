package com.example.diceless.app.di

import com.example.diceless.data.datasource.local.dao.BackgroundProfileDao
import com.example.diceless.data.datasource.local.dao.MatchDao
import com.example.diceless.data.datasource.local.dao.MatchHistoryDao
import com.example.diceless.data.datasource.local.dao.PlayerDao
import com.example.diceless.data.datasource.remote.api.ScryfallApi
import com.example.diceless.data.repository.MatchDataRepositoryImpl
import com.example.diceless.data.repository.MatchHistoryRepositoryImpl
import com.example.diceless.data.repository.PlayerProfileRepositoryImpl
import com.example.diceless.data.repository.PlayerRepositoryImpl
import com.example.diceless.data.repository.ProfileRepositoryImpl
import com.example.diceless.data.repository.ScryfallRepositoryImpl
import com.example.diceless.domain.repository.MatchDataRepository
import com.example.diceless.domain.repository.MatchHistoryRepository
import com.example.diceless.domain.repository.PlayerProfileRepository
import com.example.diceless.domain.repository.PlayerRepository
import com.example.diceless.domain.repository.ProfileRepository
import com.example.diceless.domain.repository.ScryfallRepository
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