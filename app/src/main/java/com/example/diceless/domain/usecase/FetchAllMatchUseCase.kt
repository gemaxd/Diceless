package com.example.diceless.domain.usecase

import com.example.diceless.data.entity.toDomain
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.repository.MatchHistoryRepository
import javax.inject.Inject

class FetchAllMatchUseCase @Inject constructor(
    val matchHistoryRepository: MatchHistoryRepository
) {
    suspend operator fun invoke(): List<MatchData> {
         return matchHistoryRepository.fetchAllMatchData()
             .map { it.toDomain() }
    }
}