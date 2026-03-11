package com.example.diceless.app.di

import com.example.diceless.domain.match.reducer.MatchMiddleware
import com.example.diceless.domain.match.reducer.MatchReducer
import com.example.diceless.domain.match.reducer.MatchState
import com.example.diceless.domain.match.reducer.MatchStore
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