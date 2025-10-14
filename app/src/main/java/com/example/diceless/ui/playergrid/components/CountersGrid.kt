package com.example.diceless.ui.playergrid.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.PlayerData

@Composable
fun CountersGrid(playerData: PlayerData, rotationEnum: RotationEnum) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        when(rotationEnum){
            RotationEnum.NONE, RotationEnum.INVERTED -> {
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(alignment = Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        playerData.counters.forEach {
                            CounterContent(icon = it.icon, value = it.value, rotationEnum = rotationEnum)
                        }
                    }
                }
            }
            RotationEnum.RIGHT, RotationEnum.LEFT -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(alignment = Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        playerData.counters.forEach {
                            VerticalCounterContent(icon = it.icon, value = it.value, rotationEnum = rotationEnum)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VerticalCounterContent(icon: ImageVector, value: Int?, rotationEnum: RotationEnum){
    Column {
        IconButton (
            modifier = Modifier
                .vertical()
                .padding(horizontal = 16.dp)
                .rotate(rotationEnum.degrees),
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                contentDescription = "",
                imageVector = icon
            )
        }
    }
}

@Composable
fun CounterContent(icon: ImageVector, value: Int?, rotationEnum: RotationEnum) {
    Row {
        IconButton (
            modifier = Modifier
                .vertical()
                .padding(16.dp)
                .rotate(rotationEnum.degrees),
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                contentDescription = "",
                imageVector = icon
            )
        }
    }
}