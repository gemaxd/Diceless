package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryRegistry
import com.example.diceless.domain.model.toEntity
import com.example.diceless.domain.repository.MatchDataRepository
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class RegisterMatchHistoryUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(matchHistoryRegistry: MatchHistoryRegistry) {
         return matchHistoryRepository.insertHistoryChange(historyRegistry = matchHistoryRegistry.toEntity())
    }
}