package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.MatchData
import com.manarimjesse.diceless.domain.model.toEntity
import com.manarimjesse.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class RegisterMatchUseCase @Inject constructor(
    val matchDataRepository: MatchDataRepository
) {
    suspend operator fun invoke(matchData: MatchData): Long {
         return matchDataRepository.registerMatchData(matchDataEntity = matchData.toEntity())
    }
}