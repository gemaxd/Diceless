package com.example.diceless.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.diceless.navigation.battleGrid
import com.example.diceless.navigation.route.BattleGridRoute

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BattleGridRoute.BattleGrid.route
    ) {
        battleGrid(
            onNavigation = { route ->
                navController.navigate(route)
            }
        )
    }
}