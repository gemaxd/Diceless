package com.example.diceless.ui.playergrid.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun LifeGrid(
    playerData: PlayerData,
    rotation: RotationEnum = RotationEnum.NONE
) {
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
                            onClick = { playerData.life -= 1 }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterStart),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { playerData.life += 1 }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd),
                ){}

                Text(
                    modifier = Modifier.align(
                        alignment = Alignment.Center
                    ),
                    text = "${playerData.life}",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        RotationEnum.INVERTED -> {
            Box(
                modifier = Modifier
                    .rotate(rotation.degrees)
                    .fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { playerData.life -= 1 }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { playerData.life += 1 }
                        )
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .align(Alignment.CenterStart),
                ){}

                Text(
                    modifier = Modifier.align(
                        alignment = Alignment.Center
                    ),
                    text = "${playerData.life}",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
        RotationEnum.RIGHT -> {
            Box(modifier = Modifier.fillMaxSize(),) {
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { playerData.life -= 1 }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.TopCenter),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { playerData.life += 1 }
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
                    text = "${playerData.life}",
                    style = MaterialTheme.typography.headlineLarge
                )

            }
        }
        RotationEnum.LEFT -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { playerData.life -= 1 }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.BottomCenter),
                ){}

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { playerData.life += 1 }
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
                    text = "${playerData.life}",
                    style = MaterialTheme.typography.headlineLarge
                )
            }

        }
    }
}
