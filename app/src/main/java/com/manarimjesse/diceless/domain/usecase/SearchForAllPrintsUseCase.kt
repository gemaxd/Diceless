package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.ScryfallCard
import com.manarimjesse.diceless.domain.repository.ScryfallRepository
import javax.inject.Inject

class SearchForAllPrintsUseCase @Inject constructor(
    private val repository: ScryfallRepository
) {
    suspend operator fun invoke(printsSearchUri: String): Result<List<ScryfallCard>> = runCatching {
        return repository.searchForAllPrints(printsSearchUri)
    }
}