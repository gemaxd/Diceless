package com.example.diceless.data

import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.model.extension.ScryfallSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ScryfallApi {

    @Headers(
        "User-Agent: DiceLessApp/1.0 (Android)",
        "Accept: application/json"
    )
    @GET("cards/search")
    suspend fun searchCards(
        @Query("q") query: String,
        @Query("order") order: String = "name",
        @Query("dir") dir: String = "auto",
        @Query("page") page: Int = 1
    ): ScryfallSearchResponse

    @Headers(
        "User-Agent: DiceLessApp/1.0 (Android)",
        "Accept: application/json"
    )
    @GET
    suspend fun getPrints(
        @Url printsSearchUri: String
    ): ScryfallSearchResponse

}