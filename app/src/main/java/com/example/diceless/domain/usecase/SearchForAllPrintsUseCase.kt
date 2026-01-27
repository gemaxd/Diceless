package com.example.diceless.domain.usecase

import com.example.diceless.data.repository.ScryFallRepository
import com.example.diceless.domain.model.ScryfallCard
import javax.inject.Inject

class SearchForAllPrintsUseCase @Inject constructor(
    private val repository: ScryFallRepository
) {
    suspend operator fun invoke(printsSearchUri: String): Result<List<ScryfallCard>> = runCatching {
        return repository.searchForAllPrints(printsSearchUri)
    }
}