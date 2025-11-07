package com.example.diceless.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.diceless.navigation.route.CardSearchRoute
import com.example.diceless.presentation.screens.CardSearchScreen

@OptIn(ExperimentalMaterial3Api::class)
internal fun NavGraphBuilder.cardSearch(
    onNavigation: (route: String) -> Unit
) {
    composable(
        route = CardSearchRoute.CardSearch.route
    ) {
        CardSearchScreen()
    }
}