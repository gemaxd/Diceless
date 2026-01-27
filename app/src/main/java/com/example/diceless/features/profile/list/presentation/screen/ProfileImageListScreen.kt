package com.example.diceless.features.profile.list.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.diceless.domain.model.BackgroundProfileData
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.features.profile.list.mvi.ProfileImageListActions
import com.example.diceless.features.profile.list.mvi.ProfileImageListResult
import com.example.diceless.features.profile.list.mvi.ProfileListState
import com.example.diceless.features.profile.list.presentation.viewmodel.ProfileImageListViewModel
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.RESULT_CARD_SELECTED
import com.example.diceless.navigation.RESULT_PLAYER_USED
import com.example.diceless.navigation.ResultStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImageListScreen(
    resultStore: ResultStore,
    viewModel: ProfileImageListViewModel = hiltViewModel(),
    playerData: PlayerData
){
    val navigator = LocalNavigator.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ProfileImageListResult.OnImageSelect -> {
                    resultStore.setResult(RESULT_CARD_SELECTED, event.backgroundProfile)
                    resultStore.setResult(RESULT_PLAYER_USED, event.playerData)
                    navigator.pop()
                }
            }
        }
    }

    val onUiEvent = viewModel::onAction

    Scaffold(
        topBar = {
            Row {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Procure sua carta"
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    when (val state = viewModel.state) {
                        is ProfileListState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        is ProfileListState.Success -> {
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(state.cards) { card ->
                                    CardItem(
                                        playerData = playerData,
                                        profile = card,
                                        onUiEvent = onUiEvent
                                    )
                                }
                            }
                        }
                        is ProfileListState.Error -> {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    )
}

@Composable
fun CardItem(
    playerData: PlayerData,
    profile: BackgroundProfileData,
    onUiEvent: (ProfileImageListActions) -> Unit
) {
    val imageUrl = profile.imageUri

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = profile.cardName,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onUiEvent(
                        ProfileImageListActions.OnProfileImageSelected(
                            playerData = playerData,
                            backgroundProfileData = profile
                        )
                    )
                },
            contentScale = ContentScale.Crop
        )
    }
}