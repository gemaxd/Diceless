package com.manarimjesse.diceless.app.di

import com.manarimjesse.diceless.domain.match.reducer.MatchState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideInitialMatchState(): MatchState {
        return MatchState.initial()
    }

}