package com.example.diceless.domain.usecase

import com.example.diceless.data.ScryFallRepository
import com.example.diceless.domain.model.ScryfallCard
import javax.inject.Inject

class SearchForCardsUseCase @Inject constructor(
    private val repository: ScryFallRepository
) {
    suspend operator fun invoke(query: String): Result<List<ScryfallCard>> = runCatching {
        return repository.searchCards(query = query)
    }
}