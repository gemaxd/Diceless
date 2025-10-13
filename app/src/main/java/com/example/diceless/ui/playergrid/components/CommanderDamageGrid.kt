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
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.domain.model.PlayerData

@Composable
fun CommanderDamageGrid(playerData: PlayerData, players: List<PlayerData>, rotationEnum: RotationEnum) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Text("Dano de Commander: ${playerData.commanderDamage}", style = MaterialTheme.typography.headlineMedium)
//        Row {
//            Button(onClick = { playerData.commanderDamage += 1 }) { Text("+") }
//            Spacer(Modifier.width(8.dp))
//            Button(onClick = { playerData.commanderDamage = maxOf(0, playerData.commanderDamage - 1) }) { Text("-") }
//        }
    }
}