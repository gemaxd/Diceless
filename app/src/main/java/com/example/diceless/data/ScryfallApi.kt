package com.example.diceless.data

import com.example.diceless.domain.model.ScryfallCard
import retrofit2.http.GET
import retrofit2.http.Query

interface ScryfallApi {
    @GET("cards/named")
    suspend fun getCardByExactName(@Query("exact") name: String): ScryfallCard
}