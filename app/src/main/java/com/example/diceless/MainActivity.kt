package com.example.diceless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.ui.battlegrid.BattleGridScreen
import com.example.diceless.ui.theme.DicelessTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            DicelessTheme {
                BattleGridScreen()
            }
        }
    }
}