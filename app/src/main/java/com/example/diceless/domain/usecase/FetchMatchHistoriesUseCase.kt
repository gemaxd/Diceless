package com.example.diceless.domain.usecase

import com.example.diceless.data.entity.toDomain
import com.example.diceless.domain.model.MatchHistoryRegistry
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class FetchMatchHistoriesUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(matchDataId: Long): List<MatchHistoryRegistry> {
         return matchHistoryRepository.fetchMatchHistories(matchDataId)
             .map { it.toDomain() }
    }
}