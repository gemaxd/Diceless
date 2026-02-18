package com.example.diceless.features.history.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.features.history.mvi.MatchHistoryActions
import com.example.diceless.features.history.ui.MatchHistoryViewModel

@Composable
fun MatchHistoryContainer(
    matchHistoryViewModel: MatchHistoryViewModel = hiltViewModel()
) {
    val state by matchHistoryViewModel.state.collectAsState()
    val onUiEvent = matchHistoryViewModel::onAction

    LaunchedEffect(Unit) {
        onUiEvent(MatchHistoryActions.OnLoadCurrentHistory)
    }

    when {
        state.isLoading -> {
            MatchHistorySkeleton()
        }

        state.matchData != null -> {
            MatchHistoryContent(
                matchData = state.matchData,
                histories = state.currentHistories
            )
        }
    }
}