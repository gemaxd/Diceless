package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.data.datasource.local.entity.toDomain
import com.manarimjesse.diceless.domain.model.MatchHistoryRegistry
import com.manarimjesse.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class FetchMatchHistoriesUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(matchDataId: Long): List<MatchHistoryRegistry> {
         return matchHistoryRepository.fetchMatchHistories(matchDataId)
             .map { it.toDomain() }
    }
}