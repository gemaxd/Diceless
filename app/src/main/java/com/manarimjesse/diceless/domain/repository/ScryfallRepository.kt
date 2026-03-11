package com.manarimjesse.diceless.domain.repository

import com.manarimjesse.diceless.domain.model.ScryfallCard

interface ScryfallRepository {
    suspend fun searchCards(query: String): Result<List<ScryfallCard>>
    suspend fun searchForAllPrints(printsSearchUri: String): Result<List<ScryfallCard>>
}