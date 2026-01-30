package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class UpdateMatchUseCase @Inject constructor(
    val matchDataRepository: MatchDataRepository
) {
    suspend operator fun invoke(matchData: MatchData) {
        matchDataRepository.updateMatchDataPlayerQuantity(playerQuantity = matchData.playersCount, matchId = matchData.id)
    }
}