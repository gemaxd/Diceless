package com.example.diceless.features.middlemenu.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.features.bottombelt.options.diceroll.presentation.DiceRollScreen
import com.example.diceless.features.common.components.bottomsheet.DiceRollIndicators
import com.example.diceless.features.common.components.bottomsheet.GenericBottomSheet
import com.example.diceless.presentation.battlegrid.components.button.ActionPill

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomMenuComponent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onUiEvent: (BattleGridActions) -> Unit
){
    var showDiceRollDialog by remember { mutableStateOf(false) }
    val diceRollModalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showDiceRollDialog){
        GenericBottomSheet(
            sheetState = diceRollModalSheetState,
            onDismiss = { showDiceRollDialog = false },
            content = {
                DiceRollScreen()
            },
            indicators = {
                DiceRollIndicators()
            }
        )
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isExpanded,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 100
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        ) + fadeOut(animationSpec = tween(durationMillis = 400))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            ActionPill(
                onClick = {
                    onUiEvent.invoke(BattleGridActions.ToggleMonarchCounter)
                },
                text = "Monarch",
                icon = Icons.Default.Search
            )

            ActionPill(
                onClick = {
                    showDiceRollDialog = true
                },
                text = "Monarch",
                icon = Icons.Default.Search
            )
        }
    }
}