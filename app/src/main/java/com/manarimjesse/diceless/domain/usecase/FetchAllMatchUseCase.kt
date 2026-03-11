package com.manarimjesse.diceless.domain.usecase

import com.manarimjesse.diceless.data.datasource.local.entity.toDomain
import com.manarimjesse.diceless.domain.model.MatchData
import com.manarimjesse.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class FetchAllMatchUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(): List<MatchData> {
         return matchHistoryRepository.fetchAllMatchData()
             .map { it.toDomain() }
    }
}