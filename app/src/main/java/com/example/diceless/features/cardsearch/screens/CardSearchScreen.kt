package com.example.diceless.features.cardsearch.screens

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.ScryfallCard
import com.example.diceless.features.cardsearch.mvi.CardListState
import com.example.diceless.features.cardsearch.mvi.CardSearchActions
import com.example.diceless.features.cardsearch.viewmodel.CardSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSearchScreen(
    viewModel: CardSearchViewModel = hiltViewModel(),
    playerData: PlayerData
){
    var nomeCard by remember { mutableStateOf("") }
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
        bottomBar = {
            OutlinedTextField(
                value = nomeCard,
                onValueChange = {
                    nomeCard = it
                    onUiEvent(
                        CardSearchActions.OnSearchQueryChanged(nomeCard)
                    )
                },
                label = { Text("Nome do card") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.Gray
                )
            )
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
                        is CardListState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        is CardListState.Success -> {
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(state.cards) { card ->
                                    CardItem(playerData = playerData, card = card, event = onUiEvent)
                                }
                            }
                        }
                        is CardListState.Error -> {
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
fun CardItem(playerData: PlayerData, card: ScryfallCard, event: (CardSearchActions) -> Unit) {
    val imageUrl = card.imageUris?.artCrop
        ?: card.cardFaces?.firstOrNull()?.imageUris?.large


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = card.name,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = {
                            event(
                                CardSearchActions.OnImageSelect(playerData = playerData, backgroundProfile = card)
                            )
                        }
                    ),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.Gray)
            }
        }
    }

}