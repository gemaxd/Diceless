package com.example.diceless.features.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.compose.AsyncImage
import coil.compose.EqualityDelegate
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.aggregated.PlayerWithBackgroundData
import com.example.diceless.presentation.battlegrid.components.button.CounterPill
import com.example.diceless.features.battlegrid.mvi.BattleGridActions

@Composable
fun LifeGrid(
    playerData: PlayerData,
    rotation: RotationEnum = RotationEnum.NONE,
    isCmdDamageLinked: Boolean,
    onAction: (BattleGridActions) -> Unit
) {
    val scrollState = rememberScrollState()

    when (rotation) {
        RotationEnum.NONE -> {
            Box(
                modifier = Modifier
                    .rotate(rotation.degrees)
                    .fillMaxSize(),
            ) {
                AsyncImage(
                    model = playerData.backgroundProfile?.imageUri,
                    contentDescription = playerData.backgroundProfile?.cardName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 1f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 1f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeDecreased(playerData))
                            }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterStart),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeIncreased(playerData))
                            }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd),
                ){}

                Text(
                    modifier = Modifier.align(
                        alignment = Alignment.Center
                    ),
                    text = "${playerData.getCorrectLifeValue(isCmdDamageLinked)}",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize * 2
                    ),
                    color = Color.White
                )

                if (playerData.counters.any { it.isSelected }){
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .horizontalScroll(scrollState)
                            .align(Alignment.BottomCenter)
                    ) {
                        playerData.counters.filter { it.isSelected }.forEach { selectCounter ->
                            CounterPill(
                                counterData = selectCounter
                            )
                        }
                    }
                }
            }
        }

        RotationEnum.INVERTED -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                AsyncImage(
                    model = playerData.backgroundProfile?.imageUri,
                    contentDescription = playerData.backgroundProfile?.cardName,
                    modifier = Modifier.fillMaxSize().rotate(RotationEnum.INVERTED.degrees),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                if (playerData.counters.any { it.isSelected }){
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .rotate(RotationEnum.INVERTED.degrees)
                            .fillMaxWidth()
                            .horizontalScroll(scrollState)
                            .align(Alignment.TopCenter)
                    ) {
                        playerData.counters.filter { it.isSelected }.forEach { selectCounter ->
                            CounterPill(
                                counterData = selectCounter
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeDecreased(playerData))
                            }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeIncreased(playerData))
                            }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterStart),
                ){}

                Text(
                    modifier = Modifier
                        .rotate(rotation.degrees)
                        .align(
                            alignment = Alignment.Center
                        ),
                    text = "${playerData.getCorrectLifeValue(isCmdDamageLinked)}",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize * 2
                    ),
                    color = Color.White
                )
            }
        }

        RotationEnum.RIGHT -> {
            Box(modifier = Modifier.fillMaxSize(),) {
                AsyncImage(
                    model = playerData.backgroundProfile?.imageUri,
                    contentDescription = playerData.backgroundProfile?.cardName,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            translationX = -213f
                            rotationZ = rotation.degrees
                            if (rotation.degrees % 180 != 0f) {
                                scaleX = 2.35f
                                scaleY = 0.8f
                            }
                        },
                    alignment = Alignment.TopStart
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startX = 0f,
                                endX = Float.POSITIVE_INFINITY
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeDecreased(playerData))
                            }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.TopCenter),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeIncreased(playerData))
                            }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.BottomCenter),
                ){}

                Text(
                    modifier = Modifier
                        .vertical()
                        .rotate(rotation.degrees)
                        .align(Alignment.Center),
                    text = "${playerData.getCorrectLifeValue(isCmdDamageLinked)}",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize * 1.5
                    ),
                    color = Color.White
                )

            }
        }

        RotationEnum.LEFT -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = playerData.backgroundProfile?.imageUri,
                    contentDescription = playerData.backgroundProfile?.cardName,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            translationX = 213f
                            rotationZ = rotation.degrees
                            if (rotation.degrees % 180 != 0f) {
                                scaleX = 2.35f
                                scaleY = 0.8f
                            }
                        },
                    alignment = Alignment.Center
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startX = 0f,
                                endX = Float.POSITIVE_INFINITY
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeDecreased(playerData))
                            }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.BottomCenter),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(BattleGridActions.OnLifeIncreased(playerData))
                            }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.TopCenter),
                ){}

                Text(
                    modifier = Modifier
                        .vertical()
                        .rotate(rotation.degrees)
                        .align(Alignment.Center),
                    text = "${playerData.getCorrectLifeValue(isCmdDamageLinked)}",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize * 1.5
                    ),
                    color = Color.White
                )
            }
        }
    }
}
