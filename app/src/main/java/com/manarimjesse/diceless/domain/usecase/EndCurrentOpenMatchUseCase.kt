package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class EndCurrentOpenMatchUseCase @Inject constructor(
    private val repository: MatchDataRepository
) {
    suspend operator fun invoke(matchId: Long, finishedAt: Long) =
        repository.endCurrentMatch(finishedAt, matchId)
}