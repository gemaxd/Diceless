package com.manarimjesse.diceless.features.history.mvi

import com.manarimjesse.diceless.domain.model.MatchData
import com.manarimjesse.diceless.domain.model.MatchHistoryRegistry

data class MatchHistoryState(
    val matchData: MatchData? = null,
    val currentHistories: List<MatchHistoryRegistry> = emptyList(),
    val isLoading: Boolean = false,
    val matchList: List<MatchData> = emptyList()
)
