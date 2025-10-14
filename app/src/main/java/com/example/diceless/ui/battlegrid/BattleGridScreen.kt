package com.example.diceless.ui.battlegrid

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.utils.getCorrectOrientation
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel
import com.example.diceless.ui.playergrid.components.pager.InnerHorizontalPager
import com.example.diceless.ui.playergrid.components.pager.InnerVerticalPager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions

@Composable
fun BattleGridScreen(
    viewmodel: BattleGridViewModel = hiltViewModel()
){
    val state by viewmodel.state.collectAsState()

    BattleGridContent(
        players = state.players,
        selectedScheme = state.selectedScheme,
        onAction = viewmodel::onAction
    )
}

@Composable
fun BattleGridContent(
    players: List<PlayerData>,
    selectedScheme: SchemeEnum,
    onAction: (BattleGridActions) -> Unit//(player: PlayerData, amount: Int) -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Posiciona cada PlayerGrid em um quadrante
        players.forEachIndexed { index, playerState ->
            val orientation = getCorrectOrientation(playerState.playerPosition, scheme = selectedScheme)

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
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@Composable
fun IndividualGridContent(
    modifier: Modifier = Modifier,
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum,
    onAction: (BattleGridActions) -> Unit
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
                    InnerHorizontalPager(players, playerData, rotation, onAction)
                }
                RotationEnum.RIGHT, RotationEnum.LEFT -> {
                    InnerVerticalPager(players, playerData, rotation, onAction)
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

    BattleGridContent(
        players = players,
        selectedScheme = schemeEnum,
        onAction = {}
    )
}
