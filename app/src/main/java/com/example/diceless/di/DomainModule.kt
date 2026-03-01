package com.example.diceless.di

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

    @Provides
    fun provideMatchStore(
        reducer: MatchReducer,
        middleware: MatchMiddleware,
        initialState: MatchState
    ): MatchStore {
        return MatchStore(
            reducer = reducer,
            middleware = middleware,
            initialState = initialState
        )
    }

}