package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.ScryfallCard
import com.manarimjesse.diceless.domain.repository.ScryfallRepository
import javax.inject.Inject

class SearchForCardsUseCase @Inject constructor(
    private val repository: ScryfallRepository
) {
    suspend operator fun invoke(query: String): Result<List<ScryfallCard>> = runCatching {
        return repository.searchCards(query = query)
    }
}