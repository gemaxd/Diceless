package com.example.diceless.features.history.mvi

import com.example.diceless.domain.model.MatchData

sealed class MatchHistoryActions {
    data object OnLoadCurrentHistory : MatchHistoryActions()
    data object OnLoadAllMatches: MatchHistoryActions()
    data class OnLoadHistoryById(val matchId: Long): MatchHistoryActions()
}