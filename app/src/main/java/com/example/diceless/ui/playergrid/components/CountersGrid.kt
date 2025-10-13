package com.example.diceless.ui.playergrid.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.extensions.vertical
import com.example.diceless.domain.model.PlayerData

@Composable
fun CountersGrid(playerData: PlayerData, rotationEnum: RotationEnum) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        when(rotationEnum){
            RotationEnum.NONE, RotationEnum.INVERTED -> {
                LazyRow(
                    modifier = Modifier.align(alignment = Alignment.BottomCenter)
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
                    modifier = Modifier.align(alignment = Alignment.CenterStart)
                ) {
                    item {
                        playerData.counters.forEach {
                            CounterContent(icon = it.icon, value = it.value, rotationEnum = rotationEnum)
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
        Icon(
            modifier = Modifier
                .vertical()
                .padding(horizontal = 8.dp)
                .rotate(rotationEnum.degrees),
            contentDescription = "",
            imageVector = icon
        )
    }
}

@Composable
fun CounterContent(icon: ImageVector, value: Int?, rotationEnum: RotationEnum) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .rotate(rotationEnum.degrees),
            contentDescription = "",
            imageVector = icon
        )
    }
}