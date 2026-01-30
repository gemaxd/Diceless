package com.example.diceless.domain.usecase

import com.example.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class EndCurrentOpenMatchUseCase @Inject constructor(
    private val repository: MatchDataRepository
) {
    suspend operator fun invoke(matchId: Long, finishedAt: Long) =
        repository.endCurrentMatch(finishedAt, matchId)
}