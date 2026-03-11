package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.repository.ScryfallRepository
import javax.inject.Inject

class SearchForCardsUseCase @Inject constructor(
    private val repository: ScryfallRepository
) {
    suspend operator fun invoke(query: String): Result<List<ScryfallCard>> = runCatching {
        return repository.searchCards(query = query)
    }
}