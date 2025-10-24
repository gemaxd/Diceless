package com.example.diceless.ui.battlegrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.diceless.ui.battlegrid.components.dialog.RestartDialog
import com.example.diceless.ui.battlegrid.components.dialog.SettingsDialog
import com.example.diceless.ui.battlegrid.components.pager.InnerHorizontalPager
import com.example.diceless.ui.battlegrid.components.pager.InnerVerticalPager
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel

@ExperimentalMaterial3Api
@Composable
fun BattleGridScreen(
    viewmodel: BattleGridViewModel = hiltViewModel()
) {
    val state by viewmodel.state.collectAsState()

    BattleGridContent(
        isDamageLinked = state.linkCommanderDamageToLife,
        players = state.players,
        selectedScheme = state.selectedScheme,
        onAction = viewmodel::onAction
    )
}

@ExperimentalMaterial3Api
@Composable
fun BattleGridContent(
    isDamageLinked: Boolean,
    players: List<PlayerData>,
    selectedScheme: SchemeEnum,
    onAction: (BattleGridActions) -> Unit
) {
    var showHistoryBottomSheet by remember { mutableStateOf(false) }
    var showSettingsBottomSheet by remember { mutableStateOf(false) }
    var showUsersBottomSheet by remember { mutableStateOf(false) }
    var showRestartDialog by remember { mutableStateOf(false) }

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


    Box(
        contentAlignment = Alignment.Center
    ) {
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
                            isDamageLinked = isDamageLinked,
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
