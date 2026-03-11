package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.domain.model.MatchHistoryRegistry
import com.manarimjesse.diceless.domain.model.toEntity
import com.manarimjesse.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class RegisterMatchHistoryUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(matchHistoryRegistry: MatchHistoryRegistry) {
         return matchHistoryRepository.insertHistoryChange(historyRegistry = matchHistoryRegistry.toEntity())
    }
}