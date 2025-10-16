package com.example.diceless.ui.battlegrid.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.CommanderDamage
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions

@Composable
fun CommanderDamageGrid(
    playerData: PlayerData,
    rotationEnum: RotationEnum,
    onAction: (BattleGridActions) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        when (rotationEnum) {
            RotationEnum.NONE, RotationEnum.INVERTED -> {
                Row {
                    playerData.commanderDamageReceived.forEach { cmd ->
                        HorizontalCommanderDamageControlCell(
                            playerData = playerData,
                            rotationEnum = rotationEnum,
                            cmdDamage = cmd,
                            onAction = onAction
                        )
                    }
                }
            }

            RotationEnum.RIGHT, RotationEnum.LEFT -> {
                Text(
                    modifier = Modifier
                        .vertical()
                        .rotate(rotationEnum.degrees),
                    text = "Dano de Commander",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun HorizontalCommanderDamageControlCell(
    playerData: PlayerData,
    rotationEnum: RotationEnum,
    cmdDamage: CommanderDamage,
    onAction: (BattleGridActions) -> Unit
) {
    Card(
        modifier = Modifier
            .rotate(rotationEnum.degrees)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(50)
    ) {
        Box(
            modifier = Modifier.padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = cmdDamage.damage.toString())

            Column {
                IconButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = {
                        onAction(
                            BattleGridActions.OnCommanderDamageChanged(
                                receivingPlayer = playerData,
                                playerName = cmdDamage.name,
                                amount = 1
                            )
                        )
                    }
                ) {
                    Icon(
                        contentDescription = "raise commander damage",
                        imageVector = Icons.Filled.KeyboardArrowUp,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                IconButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = {
                        onAction(
                            BattleGridActions.OnCommanderDamageChanged(
                                receivingPlayer = playerData,
                                playerName = cmdDamage.name,
                                amount = -1
                            )
                        )
                    }
                ) {
                    Icon(
                        contentDescription = "lower commander damage",
                        imageVector = Icons.Filled.KeyboardArrowDown,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCommanderDamageControl() {
    val cmdDamage = CommanderDamage(name = "Teste", damage = 10)
    val playerData = PlayerData(name = "Teste", playerPosition = PositionEnum.PLAYER_ONE)

    HorizontalCommanderDamageControlCell(
        playerData = playerData,
        rotationEnum = RotationEnum.NONE,
        cmdDamage = cmdDamage,
        onAction = {}
    )
}