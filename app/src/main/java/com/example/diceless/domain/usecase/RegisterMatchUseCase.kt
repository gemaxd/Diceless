package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.toEntity
import com.example.diceless.domain.repository.MatchDataRepository
import javax.inject.Inject

class RegisterMatchUseCase @Inject constructor(
    val matchDataRepository: MatchDataRepository
) {
    suspend operator fun invoke(matchData: MatchData): Long {
         return matchDataRepository.registerMatchData(matchDataEntity = matchData.toEntity())
    }
}