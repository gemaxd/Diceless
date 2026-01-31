package com.example.diceless.features.history.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.features.history.mvi.MatchHistoryActions
import com.example.diceless.features.history.ui.MatchHistoryViewModel

@Composable
fun MatchHistoryScreen(
    matchHistoryViewModel: MatchHistoryViewModel = hiltViewModel()
) {
    val state = matchHistoryViewModel.state.collectAsState()
    val onUiEvent = matchHistoryViewModel::onAction

    LaunchedEffect(Unit) {
        onUiEvent(MatchHistoryActions.OnLoadCurrentHistory)
    }

    when {
        state.value.isLoading -> {
            MatchHistorySkeleton()
        }

        state.value.matchData != null -> {
            MatchHistoryList(
                matchData = state.value.matchData,
                histories = state.value.histories
            )
        }
    }
}