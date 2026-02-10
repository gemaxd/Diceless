package com.example.diceless.features.bottombelt.options.diceroll.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.features.bottombelt.options.diceroll.mvi.DiceRollActions
import com.example.diceless.features.bottombelt.options.diceroll.viewmodel.DiceRollViewModel

@Composable
fun DiceRollScreen(
    viewModel: DiceRollViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(state.diceList) { diceState ->
            DiceRollItem(
                diceState = diceState,
                onClick = {
                    viewModel.onAction(
                        DiceRollActions.OnDiceClicked(diceState.dice.id)
                    )
                }
            )
        }
    }
}