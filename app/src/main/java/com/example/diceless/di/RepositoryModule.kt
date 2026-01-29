package com.example.diceless.di

import com.example.diceless.data.repository.PlayerProfileRepository
import com.example.diceless.data.repository.PlayerProfileRepositoryImpl
import com.example.diceless.data.repository.ScryFallRepository
import com.example.diceless.data.repository.ScryFallRepositoryImpl
import com.example.diceless.data.ScryfallApi
import com.example.diceless.data.dao.BackgroundProfileDao
import com.example.diceless.data.dao.GameSchemeDao
import com.example.diceless.data.dao.MatchDao
import com.example.diceless.data.dao.PlayerDao
import com.example.diceless.data.repository.GameSchemeRepositoryImpl
import com.example.diceless.data.repository.MatchHistoryRepositoryImpl
import com.example.diceless.data.repository.PlayerRepositoryImpl
import com.example.diceless.data.repository.ProfileRepositoryImpl
import com.example.diceless.domain.repository.GameSchemeRepository
import com.example.diceless.domain.repository.MatchHistoryRepository
import com.example.diceless.domain.repository.PlayerRepository
import com.example.diceless.domain.repository.ProfileRepository
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
    fun provideCardRepository(api: ScryfallApi): ScryFallRepository =
        ScryFallRepositoryImpl(api)

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
    fun provideGameSchemeRepository(
        gameSchemeDao: GameSchemeDao
    ): GameSchemeRepository = GameSchemeRepositoryImpl( gameSchemeDao = gameSchemeDao)

    @Provides
    @Singleton
    fun provideMatchHistoryRepository(
        matchDao: MatchDao
    ): MatchHistoryRepository = MatchHistoryRepositoryImpl( matchDao = matchDao)
}