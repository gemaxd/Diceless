package com.example.diceless.domain.usecase

import com.example.diceless.domain.model.GameSchemeData
import com.example.diceless.domain.repository.MatchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentOpenMatchUseCase @Inject constructor(
    private val repository: MatchHistoryRepository
) {
    suspend operator fun invoke(): Long? =
        repository.fetchCurrentOpenMatch()
}