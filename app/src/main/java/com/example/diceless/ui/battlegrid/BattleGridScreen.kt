package com.example.diceless.ui.battlegrid

import android.R.attr.visible
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
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
import com.example.diceless.ui.battlegrid.components.button.ActionPill
import com.example.diceless.ui.battlegrid.components.dialog.RestartDialog
import com.example.diceless.ui.battlegrid.components.dialog.SettingsDialog
import com.example.diceless.ui.battlegrid.components.draggable.MonarchDraggableComponent
import com.example.diceless.ui.battlegrid.components.pager.InnerHorizontalPager
import com.example.diceless.ui.battlegrid.components.pager.InnerVerticalPager
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.mvi.BattleGridState
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel

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
    var showHistoryBottomSheet by remember { mutableStateOf(false) }
    var showSettingsBottomSheet by remember { mutableStateOf(false) }
    var showUsersBottomSheet by remember { mutableStateOf(false) }
    var showRestartDialog by remember { mutableStateOf(false) }
    var expandedMiddleMenu by remember { mutableStateOf(false) }

    if (showRestartDialog) {
        RestartDialog(
            onDismiss = {showRestartDialog = false},
            onRestart = {
                onAction(BattleGridActions.OnRestart)
            }
        )
    }

    if (showHistoryBottomSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showHistoryBottomSheet = false },
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxSize(), // Ocupa toda a tela
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Modal Fullscreen",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                showHistoryBottomSheet = false
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Fechar")
                            }
                        }
                    )
                }
            ) {
                Column(modifier = Modifier.padding(it)) {
                    repeat(20) {
                        Text(
                            text = "Item $it no Modal",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }

    if (showSettingsBottomSheet) {
        SettingsDialog(
            onDismiss = {showSettingsBottomSheet = false},
        )
    }

    if (showUsersBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showUsersBottomSheet = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Settings", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Exibindo o settings dos jogadores...")
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { showUsersBottomSheet = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Fechar")
                }
            }
        }
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
                    action = { showHistoryBottomSheet = true },
                    menuItemEnum = MenuItemEnum.HISTORY
                ).getIconButton(),
                MenuItem(
                    action = { showRestartDialog = true },
                    menuItemEnum = MenuItemEnum.RESTART
                ).getIconButton()
            ),
            secondRow = listOf(
                MenuItem(
                    action = { showSettingsBottomSheet = true },
                    menuItemEnum = MenuItemEnum.SETTINGS
                ).getIconButton(),
                MenuItem(
                    action = { showUsersBottomSheet = true },
                    menuItemEnum = MenuItemEnum.SCHEMES
                ).getIconButton()
            )
        )

        AnimatedVisibility(
            visible = uiState.showMonarchSymbol,
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
            MonarchDraggableComponent(schemeEnum = selectedScheme, heightProportion = maxHeight)
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
