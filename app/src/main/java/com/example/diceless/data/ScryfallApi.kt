package com.example.diceless.data

import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.model.extension.ScryfallSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScryfallApi {

    // Busca por trecho (fuzzy)
    @GET("cards/search")
    suspend fun searchCards(
        @Query("q") query: String,
        @Query("order") order: String = "name",
        @Query("dir") dir: String = "auto",
        @Query("page") page: Int = 1
    ): ScryfallSearchResponse

}