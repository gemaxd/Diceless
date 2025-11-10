package com.example.diceless.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.diceless.navigation.route.BattleGridRoute
import com.example.diceless.features.battlegrid.BattleGridScreen

@OptIn(ExperimentalMaterial3Api::class)
internal fun NavGraphBuilder.battleGrid(
    onNavigation: (route: String) -> Unit
) {
    composable(
        route = BattleGridRoute.BattleGrid.route
    ) {
        BattleGridScreen(
            onNavigation = onNavigation
        )
    }
}