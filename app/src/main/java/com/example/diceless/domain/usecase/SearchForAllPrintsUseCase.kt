package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.domain.repository.ScryfallRepository
import javax.inject.Inject

class SearchForAllPrintsUseCase @Inject constructor(
    private val repository: ScryfallRepository
) {
    suspend operator fun invoke(printsSearchUri: String): Result<List<ScryfallCard>> = runCatching {
        return repository.searchForAllPrints(printsSearchUri)
    }
}