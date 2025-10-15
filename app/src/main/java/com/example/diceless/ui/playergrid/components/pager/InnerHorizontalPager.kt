package com.example.diceless.ui.playergrid.components.pager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.playergrid.components.CommanderDamageGrid
import com.example.diceless.ui.playergrid.components.CountersGrid
import com.example.diceless.ui.playergrid.components.LifeGrid

@Composable
fun InnerHorizontalPager(
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum,
    onAction: (BattleGridActions) -> Unit
){
    when(rotation){
        RotationEnum.NONE -> RegularHorizontalPagerContent(players, playerData, rotation, onAction)
        else -> InvertedHorizontalPagerContent(players, playerData, rotation, onAction)
    }
}

@Composable
fun RegularHorizontalPagerContent(
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum,
    onAction: (BattleGridActions) -> Unit
){
    val horizontalPagerState = rememberPagerState(initialPage = 1, pageCount = {3})
    val verticalPagerState = rememberPagerState(initialPage = 1, pageCount = {2})

    VerticalPager(
        state = verticalPagerState,
        modifier = Modifier
            .fillMaxSize()
    ) { page ->
        when(page){
            1 -> {
                HorizontalPager(
                    state = horizontalPagerState,
                    modifier = Modifier
                        .fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> CommanderDamageGrid(playerData, players, rotation)
                        1 -> LifeGrid(playerData, rotation, onAction)
                        2 -> CommanderDamageGrid(playerData, players, rotation)
                    }
                }
            }
            0 -> CountersGrid(playerData, rotation, onAction)
        }
    }
}

@Composable
fun InvertedHorizontalPagerContent(
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum,
    onAction: (BattleGridActions) -> Unit
){
    val horizontalPagerState = rememberPagerState(initialPage = 1, pageCount = {3})
    val verticalPagerState = rememberPagerState(initialPage = 0, pageCount = {2})

    VerticalPager(
        state = verticalPagerState,
        modifier = Modifier
            .fillMaxSize()
    ) { page ->
        when(page){
            0 -> {
                HorizontalPager(
                    state = horizontalPagerState,
                    modifier = Modifier
                        .fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> CommanderDamageGrid(playerData, players, rotation)
                        1 -> LifeGrid(playerData, rotation, onAction)
                        2 -> CommanderDamageGrid(playerData, players, rotation)
                    }
                }
            }
            1 -> CountersGrid(playerData, rotation, onAction)
        }
    }
}