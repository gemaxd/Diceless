package com.manarimjesse.diceless.features.history.mvi

sealed class MatchHistoryActions {
    data object OnLoadCurrentHistory : MatchHistoryActions()
    data object OnLoadAllMatches: MatchHistoryActions()
    data class OnLoadHistoryById(val matchId: Long): MatchHistoryActions()
}