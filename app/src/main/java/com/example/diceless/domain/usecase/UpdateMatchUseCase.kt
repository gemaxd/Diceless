package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.repository.MatchDataRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UpdateMatchUseCase @Inject constructor(
    val matchDataRepository: MatchDataRepository
) {
    suspend operator fun invoke(matchData: MatchData) {
        matchDataRepository.updateMatchDataPlayerQuantity(
            players = Json.encodeToString(matchData.players),
            matchId = matchData.id
        )
    }
}