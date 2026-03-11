package com.manarimjesse.diceless.app.di

import android.content.Context
import androidx.room.Room
import com.manarimjesse.diceless.data.datasource.local.dao.BackgroundProfileDao
import com.manarimjesse.diceless.data.datasource.local.dao.MatchDao
import com.manarimjesse.diceless.data.datasource.local.dao.MatchHistoryDao
import com.manarimjesse.diceless.data.datasource.local.dao.PlayerDao
import com.manarimjesse.diceless.data.datasource.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Singleton = app inteiro
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "diceless_db"   // nome do arquivo .db
        )
            .fallbackToDestructiveMigration()  // só para desenvolvimento! Remova em produção
            .build()
    }

    @Provides
    @Singleton
    fun providePlayerDao(database: AppDatabase): PlayerDao {
        return database.playerDao()
    }

    @Provides
    @Singleton
    fun provideBackgroundProfileDao(database: AppDatabase): BackgroundProfileDao {
        return database.backgroundDao()
    }

    @Provides
    @Singleton
    fun provideMatchDataDao(database: AppDatabase): MatchDao {
        return database.matchDao()
    }

    @Provides
    @Singleton
    fun provideMatchHistoryDao(database: AppDatabase): MatchHistoryDao {
        return database.matchHistoryDao()
    }
}