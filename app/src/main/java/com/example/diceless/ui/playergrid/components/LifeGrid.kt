package com.example.diceless.ui.playergrid.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.PlayerData

@Composable
fun LifeGrid(
    playerData: PlayerData,
    rotation: RotationEnum = RotationEnum.NONE
) {
    when (rotation) {
        RotationEnum.NONE -> {
            Column(
                modifier = Modifier
                    .rotate(rotation.degrees)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("${playerData.life}", style = MaterialTheme.typography.headlineLarge)
                Row {
                    Button(onClick = { playerData.life += 1 }) { Text("+") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { playerData.life = maxOf(0, playerData.life - 1) }) { Text("-") }
                }
            }
        }
        RotationEnum.INVERTED -> {
            Column(
                modifier = Modifier
                    .rotate(rotation.degrees)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("${playerData.life}", style = MaterialTheme.typography.headlineLarge)
                Row {
                    Button(onClick = { playerData.life += 1 }) { Text("+") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { playerData.life = maxOf(0, playerData.life - 1) }) { Text("-") }
                }
            }
        }
        RotationEnum.RIGHT -> {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .vertical()
                        .rotate(rotation.degrees),
                    text = "${playerData.life}",
                    style = MaterialTheme.typography.headlineLarge
                )

            }
        }
        RotationEnum.LEFT -> {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .vertical()
                        .rotate(rotation.degrees),
                    text = "${playerData.life}",
                    style = MaterialTheme.typography.headlineLarge
                )
            }

        }
    }
}
