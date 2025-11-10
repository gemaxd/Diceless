package com.example.diceless.presentation.battlegrid.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.CommanderDamage
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.features.battlegrid.mvi.BattleGridActions

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
        Text(
            text = "Dano de commander",
            modifier = Modifier
                .then(
                    other = when (rotationEnum) {
                        RotationEnum.NONE ->
                            Modifier
                                .padding(top = 45.dp)
                                .rotate(rotationEnum.degrees)
                                .align(Alignment.TopCenter)

                        RotationEnum.INVERTED ->
                            Modifier
                                .padding(bottom = 45.dp)
                                .rotate(rotationEnum.degrees)
                                .align(Alignment.BottomCenter)

                        RotationEnum.RIGHT ->
                            Modifier
                                .padding(end = 10.dp)
                                .rotate(rotationEnum.degrees)
                                .vertical()
                                .align(Alignment.CenterEnd)

                        RotationEnum.LEFT ->
                            Modifier
                                .padding(start = 10.dp)
                                .rotate(rotationEnum.degrees)
                                .vertical()
                                .align(Alignment.CenterStart)
                    }
                ),
            fontSize = if (rotationEnum.isVerticalRotated()) 20.sp else 30.sp
        )

        when (rotationEnum) {
            RotationEnum.NONE, RotationEnum.INVERTED -> {
                Row {
                    playerData.commanderDamageReceived.forEach { cmd ->
                        CommanderDamageControlCell(
                            playerData = playerData,
                            rotationEnum = rotationEnum,
                            cmdDamage = cmd,
                            onAction = onAction
                        )
                    }
                }
            }

            RotationEnum.RIGHT, RotationEnum.LEFT -> {
                Column(
                    modifier = Modifier.then(
                        other = if (rotationEnum == RotationEnum.RIGHT) {
                            Modifier
                                .padding(start = 25.dp)
                                .align(Alignment.CenterStart)
                        } else {
                            Modifier
                                .padding(end = 25.dp)
                                .align(Alignment.CenterEnd)
                        }
                    )
                ) {
                    playerData.commanderDamageReceived.forEach { cmd ->
                        CommanderDamageControlCell(
                            playerData = playerData,
                            rotationEnum = rotationEnum,
                            cmdDamage = cmd,
                            onAction = onAction,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommanderDamageControlCell(
    playerData: PlayerData,
    rotationEnum: RotationEnum,
    cmdDamage: CommanderDamage,
    onAction: (BattleGridActions) -> Unit
) {
    Card(
        modifier = Modifier
            .then(
                if (rotationEnum.isVerticalRotated()) {
                    Modifier
                        .vertical()
                } else {
                    Modifier
                }
            )
            .rotate(rotationEnum.degrees)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(50)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = cmdDamage.damage.toString())

            Column {
                IconButton(
                    modifier = Modifier
                        .then(
                            if (rotationEnum.isVerticalRotated()) {
                                Modifier
                            } else {
                                Modifier.padding(4.dp)
                            }
                        ),
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

                Spacer(
                    modifier = Modifier.then(
                        if (rotationEnum.isVerticalRotated()) {
                            Modifier
                        } else {
                            Modifier.height(4.dp)
                        }
                    )
                )

                IconButton(
                    modifier = Modifier
                        .then(
                            if (rotationEnum.isVerticalRotated()) {
                                Modifier
                            } else {
                                Modifier.padding(4.dp)
                            }
                        ),
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
fun PreviewCommanderDamageGridRIGHT() {
    val playerData = PlayerData(
        name = "Teste",
        playerPosition = PositionEnum.PLAYER_ONE,
        commanderDamageReceived = mutableListOf(
            CommanderDamage(
                name = "teste",
                damage = 10
            )
        )
    )

    CommanderDamageGrid(
        playerData = playerData,
        rotationEnum = RotationEnum.RIGHT,
        onAction = {}
    )
}

@Preview
@Composable
fun PreviewCommanderDamageGridLEFT() {
    val playerData = PlayerData(
        name = "Teste",
        playerPosition = PositionEnum.PLAYER_ONE,
        commanderDamageReceived = mutableListOf(
            CommanderDamage(
                name = "teste",
                damage = 10
            )
        )
    )

    CommanderDamageGrid(
        playerData = playerData,
        rotationEnum = RotationEnum.LEFT,
        onAction = {}
    )
}

@Preview
@Composable
fun PreviewCommanderDamageGridNONE() {
    val playerData = PlayerData(
        name = "Teste",
        playerPosition = PositionEnum.PLAYER_ONE,
        commanderDamageReceived = mutableListOf(
            CommanderDamage(
                name = "teste",
                damage = 10
            )
        )
    )

    CommanderDamageGrid(
        playerData = playerData,
        rotationEnum = RotationEnum.NONE,
        onAction = {}
    )
}

@Preview
@Composable
fun PreviewCommanderDamageGridINVERTED() {
    val playerData = PlayerData(
        name = "Teste",
        playerPosition = PositionEnum.PLAYER_ONE,
        commanderDamageReceived = mutableListOf(
            CommanderDamage(
                name = "teste",
                damage = 10
            )
        )
    )

    CommanderDamageGrid(
        playerData = playerData,
        rotationEnum = RotationEnum.INVERTED,
        onAction = {}
    )
}