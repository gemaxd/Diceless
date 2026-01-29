package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.toEntity
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class RegisterMatchUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(matchData: MatchData): Long {
         return matchHistoryRepository.registerMatchData(matchDataEntity = matchData.toEntity())
    }
}