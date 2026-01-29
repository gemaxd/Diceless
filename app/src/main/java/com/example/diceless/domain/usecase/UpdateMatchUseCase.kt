package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.toEntity
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class UpdateMatchUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(matchData: MatchData) {
        matchHistoryRepository.updateMatchDataPlayerQuantity(playerQuantity = matchData.playersCount, matchId = matchData.id)
    }
}