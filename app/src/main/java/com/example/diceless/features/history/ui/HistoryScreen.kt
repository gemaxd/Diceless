package com.example.diceless.features.history.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.common.extensions.toFormattedDate
import com.example.diceless.features.history.components.HistoryDetailButton
import com.example.diceless.features.history.components.HistoryDetails
import com.example.diceless.features.history.components.HistoryHeading
import com.example.diceless.features.history.components.MatchHistoryHeader
import com.example.diceless.features.history.mvi.MatchHistoryActions
import com.example.diceless.features.history.mvi.MatchHistoryState
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.Route

@Composable
fun HistoryScreen(
    viewModel: MatchHistoryViewModel = hiltViewModel()
){
    val onUiEvent = viewModel::onAction
    val state by viewModel.state.collectAsState()

    LaunchedEffect( true ) {
        onUiEvent(
            MatchHistoryActions.OnLoadAllMatches
        )
    }

    HistoryContent(
        onUiEvent = onUiEvent,
        state = state
    )
}

@Composable
fun HistoryContent(
    onUiEvent: (MatchHistoryActions) -> Unit = {},
    state: MatchHistoryState
){
    val navigator = LocalNavigator.current

    Column {
        Text(text = "Historico")
        Text(text = "Abixo você confere a lista de partida")

        LazyColumn {
            items(state.matchList) { match ->
                MatchHistoryHeader(
                    header = {
                        HistoryHeading(matchData = match)
                    },
                    details = {
                        HistoryDetails(matchData = match)
                    },
                    append = {
                        HistoryDetailButton(
                            text = "Detalhes",
                            onActionClick = {
                                navigator.navigate(Route.HistoryDetail(match.id))
                            }
                        )
                    }
                )
            }
        }
    }
}
