package com.example.diceless.domain.repository

import com.example.diceless.domain.model.ScryfallCard

interface ScryfallRepository {
    suspend fun searchCards(query: String): Result<List<ScryfallCard>>
    suspend fun searchForAllPrints(printsSearchUri: String): Result<List<ScryfallCard>>
}