package com.example.diceless.ui.playergrid.components.pager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.playergrid.components.CommanderDamageGrid
import com.example.diceless.ui.playergrid.components.CountersGrid
import com.example.diceless.ui.playergrid.components.LifeGrid

@Composable
fun InnerVerticalPager(
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum
) {
    when (rotation) {
        RotationEnum.RIGHT -> VerticalRightPagerContent(players, playerData, rotation)
        else -> VerticalLeftPagerContent(players, playerData, rotation)
    }
}

@Composable
fun VerticalRightPagerContent(
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum
){
    val verticalPagerState = rememberPagerState(initialPage = 1, pageCount = {3})
    val horizontalPagerState = rememberPagerState(initialPage = 0, pageCount = {2})

    HorizontalPager(
        state = horizontalPagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> {
                VerticalPager(
                    state = verticalPagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> CommanderDamageGrid(playerData, players, rotation)
                        1 -> LifeGrid(playerData, rotation)
                        2 -> CommanderDamageGrid(playerData, players, rotation)
                    }
                }
            }
            1 -> CountersGrid(playerData, rotation)
        }
    }
}

@Composable
fun VerticalLeftPagerContent(
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum
){
    val verticalPagerState = rememberPagerState(initialPage = 1, pageCount = {3})
    val horizontalPagerState = rememberPagerState(initialPage = 1, pageCount = {2})

    HorizontalPager(
        state = horizontalPagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> CountersGrid(playerData, rotation)
            1 -> {
                VerticalPager(
                    state = verticalPagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> CommanderDamageGrid(playerData, players, rotation)
                        1 -> LifeGrid(playerData, rotation)
                        2 -> CommanderDamageGrid(playerData, players, rotation)
                    }
                }
            }
        }
    }
}
