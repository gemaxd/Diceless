package com.example.diceless.di

import com.example.diceless.data.ScryfallApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->

                val request = chain.request()
                    .newBuilder()
                    .addHeader(
                        "User-Agent",
                        "DiceLessApp/1.0 (Android)"
                    )
                    .addHeader(
                        "Accept",
                        "application/json"
                    )
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.scryfall.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideScryfallApi(retrofit: Retrofit): ScryfallApi {
        return retrofit.create(ScryfallApi::class.java)
    }
}