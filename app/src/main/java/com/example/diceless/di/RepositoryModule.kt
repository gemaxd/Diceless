package com.example.diceless.di

import com.example.diceless.data.ScryFallRepository
import com.example.diceless.data.ScryFallRepositoryImpl
import com.example.diceless.data.ScryfallApi
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
}