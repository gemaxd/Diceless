package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.MatchData
import com.manarimjesse.diceless.domain.model.toEntity
import com.manarimjesse.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class UpdateMatchUseCase @Inject constructor(
    val matchDataRepository: MatchDataRepository
) {
    suspend operator fun invoke(matchData: MatchData) {
        matchDataRepository.updateMatchData(
            matchDataEntity = matchData.toEntity()
        )
    }
}