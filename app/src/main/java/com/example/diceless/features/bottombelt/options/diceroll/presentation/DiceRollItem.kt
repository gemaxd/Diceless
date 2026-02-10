package com.example.diceless.features.bottombelt.options.diceroll.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.features.bottombelt.options.diceroll.mvi.DiceUIState

@Composable
fun DiceRollItem(
    diceState: DiceUIState,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (diceState.rolling) 360f else 0f,
        animationSpec = tween(400)
    )

    val scale by animateFloatAsState(
        targetValue = if (diceState.rolling) 0f else 1f,
        animationSpec = tween(400)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .size(width = 110.dp, height = 90.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (diceState.result == null) {
            if(diceState.dice.diceRes != null) {
                Image(
                    modifier = Modifier
                        .scale(scale)
                        .rotate(rotation),
                    painter = androidx.compose.ui.res.painterResource(id = diceState.dice.diceRes),
                    contentDescription = null,
                )
            } else {
                Text(
                    diceState.dice.label,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .rotate(rotation)
                        .scale(scale)

                )
            }
        } else {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    diceState.result,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}