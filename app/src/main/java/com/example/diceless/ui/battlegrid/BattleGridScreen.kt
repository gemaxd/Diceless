package com.example.diceless.ui.battlegrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.utils.getCorrectOrientation
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.battlegrid.components.MiddleMenu
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel
import com.example.diceless.ui.battlegrid.components.pager.InnerHorizontalPager
import com.example.diceless.ui.battlegrid.components.pager.InnerVerticalPager

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
    onAction: (BattleGridActions) -> Unit
){
    Box(
        contentAlignment = Alignment.Center
    ) {
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
        MiddleMenu(
            firstRow = listOf(
                {
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                },
                {
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                }
            ),
            secondRow = listOf(
                {
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                },
                {
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Face,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                }
            )
        )
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
            .padding(8.dp)
            .background(Color(0xFFBBDEFB)),
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
