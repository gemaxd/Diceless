package com.example.diceless.features.history.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.features.history.components.HistoryResumedRow
import com.example.diceless.features.history.components.MatchHistoryHeader
import com.example.diceless.features.history.mvi.MatchHistoryActions
import com.example.diceless.features.history.mvi.MatchHistoryState
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: MatchHistoryViewModel = hiltViewModel()
){
    val navigator = LocalNavigator.current
    val onUiEvent = viewModel::onAction
    val state by viewModel.state.collectAsState()

    LaunchedEffect( true ) {
        onUiEvent(
            MatchHistoryActions.OnLoadAllMatches
        )
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
                        text = "Histórico",
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
        HistoryContent(
            modifier = Modifier.padding(paddingValues = paddingValues),
            onUiEvent = onUiEvent,
            state = state
        )
    }
}

@Composable
fun HistoryContent(
    modifier: Modifier = Modifier,
    onUiEvent: (MatchHistoryActions) -> Unit = {},
    state: MatchHistoryState
){
    val navigator = LocalNavigator.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Abaixo você confere a lista de partida",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (-1).sp
        )

        LazyColumn {
            items(state.matchList) { match ->
                Card(
                    modifier = Modifier.padding(vertical = 8.dp),
                    shape = RoundedCornerShape(5),
                    onClick = {
                        navigator.navigate(Route.HistoryDetail(match.id))
                    }
                ) {
                    MatchHistoryHeader(
                        modifier = Modifier.padding(8.dp),
                        header = {
                            HistoryResumedRow(matchData = match)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewTopAppBar(){
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {

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
                text = "Histórico",
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

