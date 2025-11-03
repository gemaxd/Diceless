package com.example.diceless.ui.battlegrid

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.common.enums.MenuItemEnum
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.common.utils.getCorrectOrientation
import com.example.diceless.domain.model.MenuItem
import com.example.diceless.domain.model.PlayerData
import com.example.diceless.domain.model.extension.getIconButton
import com.example.diceless.ui.battlegrid.components.MiddleMenu
import com.example.diceless.ui.battlegrid.components.bottomsheet.GenericBottomSheet
import com.example.diceless.ui.battlegrid.components.bottomsheet.HistoryIndicators
import com.example.diceless.ui.battlegrid.components.bottomsheet.RestartIndicators
import com.example.diceless.ui.battlegrid.components.bottomsheet.SchemeIndicators
import com.example.diceless.ui.battlegrid.components.bottomsheet.SettingsIndicators
import com.example.diceless.ui.battlegrid.components.bottomsheet.containers.HistoryContainer
import com.example.diceless.ui.battlegrid.components.bottomsheet.containers.RestartContainer
import com.example.diceless.ui.battlegrid.components.bottomsheet.containers.SchemeContainer
import com.example.diceless.ui.battlegrid.components.bottomsheet.containers.SettingsContainer
import com.example.diceless.ui.battlegrid.components.button.ActionPill
import com.example.diceless.ui.battlegrid.components.draggable.MonarchDraggableComponent
import com.example.diceless.ui.battlegrid.components.pager.InnerHorizontalPager
import com.example.diceless.ui.battlegrid.components.pager.InnerVerticalPager
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.mvi.BattleGridState
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun BattleGridScreen(
    viewmodel: BattleGridViewModel = hiltViewModel()
) {
    val state by viewmodel.state.collectAsState()

    BattleGridContent(
        uiState = state,
        players = state.players,
        selectedScheme = state.selectedScheme,
        onAction = viewmodel::onAction
    )
}

@ExperimentalMaterial3Api
@Composable
fun BattleGridContent(
    uiState: BattleGridState,
    players: List<PlayerData>,
    selectedScheme: SchemeEnum,
    onAction: (BattleGridActions) -> Unit
) {
    var expandedMiddleMenu by remember { mutableStateOf(false) }

    var showUsersBottomSheet by remember { mutableStateOf(false) }
    val schemeModalSheetState = rememberModalBottomSheetState()

    var showSettingsBottomSheet by remember { mutableStateOf(false) }
    val settingsModalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var showHistoryBottomSheet by remember { mutableStateOf(false) }
    val historyModalSheetState = rememberModalBottomSheetState()

    var showRestartDialog by remember { mutableStateOf(false) }
    val restartModalSheetState = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()

    if (showRestartDialog){
        GenericBottomSheet(
            sheetState = restartModalSheetState,
            onDismiss = { showRestartDialog = false },
            content = {
                RestartContainer(
                    onDismiss = {
                        scope.launch { restartModalSheetState.hide() }.invokeOnCompletion {
                            if (!restartModalSheetState.isVisible) {
                                showRestartDialog = false
                            }
                        }
                    }
                )
            },
            indicators = {
                RestartIndicators()
            }
        )
    }

    if (showHistoryBottomSheet) {
        GenericBottomSheet(
            sheetState = historyModalSheetState,
            onDismiss = { showHistoryBottomSheet = false },
            content = {
                HistoryContainer()
            },
            indicators = {
                HistoryIndicators()
            }
        )
    }

    if (showSettingsBottomSheet) {
        GenericBottomSheet(
            sheetState = settingsModalSheetState,
            onDismiss = { showSettingsBottomSheet = false },
            content = {
                SettingsContainer()
            },
            indicators = {
                SettingsIndicators()
            }
        )
    }

    if (showUsersBottomSheet) {
        GenericBottomSheet(
            sheetState = schemeModalSheetState,
            onDismiss = { showUsersBottomSheet = false },
            content = {
                SchemeContainer(
                    onDismiss = {
                        scope.launch { schemeModalSheetState.hide() }.invokeOnCompletion {
                            if (!schemeModalSheetState.isVisible) {
                                showUsersBottomSheet = false
                            }
                        }
                    }
                )
            },
            indicators = {
                SchemeIndicators()
            }
        )
    }

    BoxWithConstraints(
        contentAlignment = Alignment.Center
    ) {
        val maxHeight = maxHeight

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Posiciona cada PlayerGrid em um quadrante
            players.forEachIndexed { index, playerState ->
                val orientation =
                    getCorrectOrientation(playerState.playerPosition, scheme = selectedScheme)

                orientation?.let {
                    Card(
                        modifier = Modifier
                            .align(it.alignment.align)
                            .fillMaxWidth(it.proportion.width)
                            .fillMaxHeight(it.proportion.height),
                    ) {
                        IndividualGridContent(
                            isDamageLinked = uiState.linkCommanderDamageToLife,
                            players = players,
                            playerData = playerState,
                            rotation = orientation.rotation,
                            onAction = onAction
                        )
                    }
                }
            }
        }
        MiddleMenu(
            expanded = expandedMiddleMenu,
            onExpandMiddleMenu = {
                expandedMiddleMenu = !expandedMiddleMenu
            },
            firstRow = listOf(
                MenuItem(
                    action = {
                        scope.launch {
                            showHistoryBottomSheet = true
                        }
                    },
                    menuItemEnum = MenuItemEnum.HISTORY
                ).getIconButton(),
                MenuItem(
                    action = {
                        scope.launch {
                            showRestartDialog = true
                        }
                    },
                    menuItemEnum = MenuItemEnum.RESTART
                ).getIconButton()
            ),
            secondRow = listOf(
                MenuItem(
                    action = {
                        scope.launch {
                            showSettingsBottomSheet = true
                        }
                    },
                    menuItemEnum = MenuItemEnum.SETTINGS
                ).getIconButton(),
                MenuItem(
                    action = {
                        scope.launch {
                            showUsersBottomSheet = true
                        }
                    },
                    menuItemEnum = MenuItemEnum.SCHEMES
                ).getIconButton()
            )
        )

        AnimatedVisibility(
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            visible = uiState.showMonarchSymbol,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 500
                )
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 400)
            )
        ) {
            MonarchDraggableComponent(schemeEnum = selectedScheme, maxHeight)
        }

        AnimatedVisibility(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            visible = expandedMiddleMenu,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 100
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(
                animationSpec = tween(durationMillis = 400)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                ActionPill(
                    onClick = {
                        onAction.invoke(BattleGridActions.ToggleMonarchCounter)
                    },
                    text = "Monarch",
                    icon = Icons.Default.Search
                )
            }
        }
    }
}

@Composable
fun IndividualGridContent(
    modifier: Modifier = Modifier,
    isDamageLinked: Boolean,
    players: List<PlayerData>,
    playerData: PlayerData,
    rotation: RotationEnum,
    onAction: (BattleGridActions) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color(0xFFBBDEFB)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            when (rotation) {
                RotationEnum.NONE, RotationEnum.INVERTED -> {
                    InnerHorizontalPager(isDamageLinked, players, playerData, rotation, onAction)
                }

                RotationEnum.RIGHT, RotationEnum.LEFT -> {
                    InnerVerticalPager(isDamageLinked, players, playerData, rotation, onAction)
                }
            }
        }
    }
}
