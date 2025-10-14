package com.example.diceless.ui.playergrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.utils.getCorrectOrientation
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.playergrid.components.pager.InnerHorizontalPager
import com.example.diceless.ui.playergrid.components.pager.InnerVerticalPager

@Composable
fun IndividualGrid(
    players: List<PlayerData> = emptyList(),
    schemeEnum: SchemeEnum = SchemeEnum.QUADRA_STANDARD
){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Posiciona cada PlayerGrid em um quadrante
        players.forEachIndexed { index, playerState ->
            val orientation = getCorrectOrientation(playerState.playerPosition, scheme = schemeEnum)

            orientation?.let {
                Card(
                    modifier = Modifier
                        .align(it.alignment.align)
                        .padding(10.dp)
                        .fillMaxWidth(it.proportion.width)
                        .fillMaxHeight(it.proportion.height),
                ) {
                    IndividualGridContent(
                        players = players,
                        playerData = playerState,
                        rotation = orientation.rotation,
                    )
                }
            }
        }
    }
}

@Composable
fun IndividualGridContent(
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB)) // Azul claro se focado
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            when(rotation){
                RotationEnum.NONE, RotationEnum.INVERTED -> {
                    InnerHorizontalPager(players, playerData, rotation)
                }
                RotationEnum.RIGHT, RotationEnum.LEFT -> {
                    InnerVerticalPager(players, playerData, rotation)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStubView() {
    val players = listOf(
        PlayerData(name ="Jogador 1", playerPosition = PositionEnum.PLAYER_ONE),
        PlayerData(name ="Jogador 2", playerPosition = PositionEnum.PLAYER_TWO),
        PlayerData(name ="Jogador 3", playerPosition = PositionEnum.PLAYER_THREE),
        PlayerData(name ="Jogador 4", playerPosition = PositionEnum.PLAYER_FOUR)
    )

    val schemeEnum = SchemeEnum.QUADRA_STANDARD

    IndividualGrid(
        players = players,
        schemeEnum = schemeEnum
    )
}
