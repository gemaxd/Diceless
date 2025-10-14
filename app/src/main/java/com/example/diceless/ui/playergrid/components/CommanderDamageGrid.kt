package com.example.diceless.ui.playergrid.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.PlayerData

@Composable
fun CommanderDamageGrid(playerData: PlayerData, players: List<PlayerData>, rotationEnum: RotationEnum) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when(rotationEnum){
            RotationEnum.NONE, RotationEnum.INVERTED -> {
                Text(
                    modifier = Modifier.rotate(rotationEnum.degrees),
                    text = "Dano de Commander",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            RotationEnum.RIGHT, RotationEnum.LEFT -> {
                Text(
                    modifier = Modifier.vertical().rotate(rotationEnum.degrees),
                    text = "Dano de Commander",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}