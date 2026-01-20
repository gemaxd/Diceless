package com.example.diceless.di

import com.example.diceless.data.PlayerProfileRepository
import com.example.diceless.data.PlayerProfileRepositoryImpl
import com.example.diceless.data.ScryFallRepository
import com.example.diceless.data.ScryFallRepositoryImpl
import com.example.diceless.data.ScryfallApi
import com.example.diceless.data.dao.PlayerDao
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
}