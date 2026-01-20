package com.example.diceless.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.diceless.features.battlegrid.BattleGridScreen
import com.example.diceless.features.cardsearch.screens.CardSearchScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@ExperimentalMaterial3Api
@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack: NavBackStack<NavKey> = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.BattleGrid::class, Route.BattleGrid.serializer())
                    subclass(Route.CardSearch::class, Route.CardSearch.serializer())
                }
            }

        },
         Route.BattleGrid
    )

    val navigator = remember {
        Navigator(backStack)
    }

    CompositionLocalProvider(
        LocalNavigator provides navigator
    ) {
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator()
            ),
            entryProvider = { key ->
                when(key) {
                    is Route.BattleGrid -> {
                        NavEntry(key) {
                            BattleGridScreen(onNavigation = {})
                        }
                    }
                    is Route.CardSearch -> {
                        NavEntry(key) {
                            CardSearchScreen(
                                playerData = key.playerData,

                                )
                        }
                    }
                    else -> error("Unknown NavKey: $key")
                }
            }
        )
    }
}