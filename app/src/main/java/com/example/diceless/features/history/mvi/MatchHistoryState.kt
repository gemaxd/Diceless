package com.example.diceless.features.history.mvi

import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryRegistry

data class MatchHistoryState(
    val matchData: MatchData? = null,
    val histories: List<MatchHistoryRegistry> = emptyList(),
    val isLoading: Boolean = false
)
