package com.example.diceless.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.diceless.features.battlegrid.presentation.BattleGridScreen
import com.example.diceless.features.profile.list.presentation.screen.ProfileImageListScreen
import com.example.diceless.features.profile.search.screens.ProfileImageSearchScreen
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
                    subclass(Route.ProfileImageSearch::class, Route.ProfileImageSearch.serializer())
                    subclass(Route.ProfileImageList::class, Route.ProfileImageList.serializer())
                }
            }

        },
         Route.BattleGrid
    )

    val navigator = remember {
        Navigator(backStack)
    }
    val resultStore = rememberResultStore()

    CompositionLocalProvider(
        LocalNavigator provides navigator
    ) {
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                when(key) {
                    is Route.BattleGrid -> {
                        NavEntry(key) {
                            BattleGridScreen(
                                resultStore = resultStore
                            )
                        }
                    }
                    is Route.ProfileImageSearch -> {
                        NavEntry(key) {
                            ProfileImageSearchScreen(
                                resultStore = resultStore,
                                playerData = key.playerData,
                            )
                        }
                    }
                    is Route.ProfileImageList -> {
                        NavEntry(key) {
                            ProfileImageListScreen(
                                resultStore = resultStore,
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