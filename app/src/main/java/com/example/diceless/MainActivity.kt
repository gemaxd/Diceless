package com.example.diceless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.diceless.navigation.graph.NavigationGraph
import com.example.diceless.presentation.battlegrid.BattleGridScreen
import com.example.diceless.presentation.theme.DicelessTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            NavigationGraph(
                modifier = Modifier,
                navController = navController
            )
            DicelessTheme {
                BattleGridScreen()
            }
        }
    }
}