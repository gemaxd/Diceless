package com.example.diceless.ui.battlegrid.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions

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
                    )
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
                    )
                )
            }
        }

        RotationEnum.RIGHT -> {
            Box(modifier = Modifier.fillMaxSize(),) {
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
                    style = MaterialTheme.typography.headlineLarge
                )

            }
        }

        RotationEnum.LEFT -> {
            Box(modifier = Modifier.fillMaxSize()) {
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
                    style = MaterialTheme.typography.headlineLarge
                )
            }

        }
    }
}
