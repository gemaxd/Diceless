package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.MatchData
import com.manarimjesse.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class GetMatchByIdUseCase @Inject constructor(
    private val repository: MatchDataRepository
) {
    suspend operator fun invoke(matchId: Long): MatchData? =
        repository.fetchMatchById(matchId = matchId)
}