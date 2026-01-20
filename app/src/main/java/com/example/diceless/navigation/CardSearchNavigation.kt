package com.example.diceless.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.diceless.navigation.route.CardSearchRoute
import com.example.diceless.features.cardsearch.screens.CardSearchScreen
import com.example.diceless.navigation.route.BattleGridRoute

@OptIn(ExperimentalMaterial3Api::class)
internal fun NavGraphBuilder.cardSearch(
    onNavigation: (route: String) -> Unit
) {
    composable(
        route = CardSearchRoute.CardSearch.route+"/{playerId}",
        arguments = listOf(
            navArgument("playerId") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) { backStackEntry ->
//        val playerId = backStackEntry.arguments?.getString("playerId")
//            ?: return@composable
//
//        CardSearchScreen(
//            playerData = playerId,
//            onBack = {
//                onNavigation(BattleGridRoute.BattleGrid.route)
//            }
//        )
    }
}