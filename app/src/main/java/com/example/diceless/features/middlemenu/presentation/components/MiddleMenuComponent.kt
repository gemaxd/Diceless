package com.example.diceless.features.middlemenu.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.diceless.common.enums.MenuItemEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.MenuItem
import com.example.diceless.domain.model.extension.getIconButton
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.features.battlegrid.mvi.BattleGridState
import com.example.diceless.presentation.battlegrid.components.MiddleMenu
import com.example.diceless.presentation.battlegrid.components.MiddleMenuSolo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiddleMenuComponent(
    uiState: BattleGridState,
    onUiEvent: (BattleGridActions) -> Unit,
    expanded: Boolean,
    onToggle: () -> Unit,
    onHistory: () -> Unit,
    onRestart: () -> Unit,
    onSettings: () -> Unit,
    onSchemes: () -> Unit
){
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.selectedScheme != SchemeEnum.SOLO){
            MiddleMenu(
                modifier = Modifier.align(Alignment.Center),
                expanded = expanded,
                onExpandMiddleMenu = { onToggle.invoke() },
                firstRow = listOf(
                    MenuItem(
                        action = { scope.launch { onHistory.invoke() } },
                        menuItemEnum = MenuItemEnum.HISTORY
                    ).getIconButton(),
                    MenuItem(
                        action = { scope.launch { onRestart.invoke() } },
                        menuItemEnum = MenuItemEnum.RESTART
                    ).getIconButton()
                ),
                secondRow = listOf(
                    MenuItem(
                        action = { scope.launch { onSettings.invoke() } },
                        menuItemEnum = MenuItemEnum.SETTINGS
                    ).getIconButton(),
                    MenuItem(
                        action = { scope.launch { onSchemes.invoke() } },
                        menuItemEnum = MenuItemEnum.SCHEMES
                    ).getIconButton()
                )
            )
        } else {
            MiddleMenuSolo(
                modifier = Modifier.align(Alignment.TopEnd),
                expanded = expanded,
                onExpandMiddleMenu = { onToggle.invoke() },
                firstRow = listOf(
                    MenuItem(
                        action = { scope.launch { onHistory.invoke() } },
                        menuItemEnum = MenuItemEnum.HISTORY
                    ).getIconButton(),
                    MenuItem(
                        action = { scope.launch { onRestart.invoke() } },
                        menuItemEnum = MenuItemEnum.RESTART
                    ).getIconButton(),
                    MenuItem(
                        action = { scope.launch { onSettings.invoke() } },
                        menuItemEnum = MenuItemEnum.SETTINGS
                    ).getIconButton(),
                    MenuItem(
                        action = { scope.launch { onSchemes.invoke() } },
                        menuItemEnum = MenuItemEnum.SCHEMES
                    ).getIconButton()
                ),
            )
        }
    }
}