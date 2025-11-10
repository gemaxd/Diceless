package com.example.diceless.features.cardsearch.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.diceless.features.cardsearch.mvi.CardImageState
import com.example.diceless.features.cardsearch.viewmodel.CardSearchViewModel

@Composable
fun CardSearchScreen(
    viewModel: CardSearchViewModel = hiltViewModel()
){
    var nomeCard by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nomeCard,
            onValueChange = { nomeCard = it },
            label = { Text("Nome do card") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.buscar(nomeCard) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar imagem")
        }

        when (val state = viewModel.state) {
            is CardImageState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is CardImageState.Success -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                ) {
                    AsyncImage(
                        model = state.url,
                        contentDescription = nomeCard,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            is CardImageState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {}
        }
    }
}