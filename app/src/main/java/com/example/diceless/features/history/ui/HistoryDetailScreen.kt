package com.example.diceless.features.history.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.features.history.components.MatchHistoryContent
import com.example.diceless.features.history.components.MatchHistorySkeleton
import com.example.diceless.features.history.mvi.MatchHistoryActions
import com.example.diceless.features.history.mvi.MatchHistoryState
import com.example.diceless.navigation.LocalNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    matchId: Long,
    matchHistoryViewModel: MatchHistoryViewModel = hiltViewModel()
) {
    val navigator = LocalNavigator.current
    val state by matchHistoryViewModel.state.collectAsState()
    val onUiEvent = matchHistoryViewModel::onAction

    LaunchedEffect(Unit) {
        onUiEvent(MatchHistoryActions.OnLoadHistoryById(matchId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.pop()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Detalhes da partida",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        contentDescription = "",
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        tint = Color.Transparent
                    )
                }
            )
        }
    ) { paddingValues ->
        HistoryDetailContent(
            modifier = Modifier.padding(paddingValues = paddingValues),
            state = state
        )
    }
}

@Composable
fun HistoryDetailContent(
    modifier: Modifier = Modifier,
    state: MatchHistoryState
){
    when {
        state.isLoading -> {
            MatchHistorySkeleton()
        }

        state.matchData != null -> {
            MatchHistoryContent(
                modifier = modifier,
                matchData = state.matchData,
                histories = state.currentHistories
            )
        }
    }
}