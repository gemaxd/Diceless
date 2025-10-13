package com.example.diceless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.playergrid.IndividualGrid
import com.example.diceless.ui.theme.DicelessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            DicelessTheme {
//                val viewModel = BattlegridViewModel()
                val players = listOf(
                    PlayerData(name ="Jogador 1", playerPosition = PositionEnum.PLAYER_ONE),
                    PlayerData(name ="Jogador 2", playerPosition = PositionEnum.PLAYER_TWO),
                    PlayerData(name = "Jogador 3", playerPosition = PositionEnum.PLAYER_THREE),
                    PlayerData(name ="Jogador 4", playerPosition = PositionEnum.PLAYER_FOUR)
                )

                val schemeEnum = SchemeEnum.TRIPLE_STANDARD
                IndividualGrid(
                    players = players,
                    schemeEnum = schemeEnum
                )
            }
        }
    }
}