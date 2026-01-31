package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class GetCurrentOpenMatchUseCase @Inject constructor(
    private val repository: MatchDataRepository
) {
    suspend operator fun invoke(): MatchData? =
        repository.fetchCurrentOpenMatch()
}