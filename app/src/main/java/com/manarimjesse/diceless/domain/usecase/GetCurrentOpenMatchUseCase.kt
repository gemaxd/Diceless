package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.MatchData
import com.manarimjesse.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class GetCurrentOpenMatchUseCase @Inject constructor(
    private val repository: MatchDataRepository
) {
    suspend operator fun invoke(): MatchData? =
        repository.fetchCurrentOpenMatch()
}