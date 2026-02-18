package com.example.diceless.features.middlemenu.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.example.diceless.features.bottombelt.options.diceroll.presentation.DiceRollScreen
import com.example.diceless.features.common.components.bottomsheet.DiceRollIndicators
import com.example.diceless.features.common.components.bottomsheet.GenericBottomSheet
import com.example.diceless.features.common.components.bottomsheet.HistoryIndicators
import com.example.diceless.features.common.components.bottomsheet.RestartIndicators
import com.example.diceless.features.common.components.bottomsheet.SchemeIndicators
import com.example.diceless.features.common.components.bottomsheet.SettingsIndicators
import com.example.diceless.features.common.components.bottomsheet.containers.RestartContainer
import com.example.diceless.features.common.components.bottomsheet.containers.SchemeContainer
import com.example.diceless.features.history.components.MatchHistoryContainer
import com.example.diceless.features.middlemenu.mvi.BattleGridSheetState
import com.example.diceless.presentation.battlegrid.components.bottomsheet.containers.SettingsContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BattleGridBottomSheetsHost(
    currentSheet: BattleGridSheetState,
    modalSheetState: SheetState,
    onDismiss: () -> Unit
) {
    when (currentSheet) {

        BattleGridSheetState.None -> Unit

        BattleGridSheetState.History -> {
            GenericBottomSheet(
                sheetState = modalSheetState,
                onDismiss = onDismiss,
                content = { MatchHistoryContainer() },
                indicators = { HistoryIndicators() }
            )
        }

        BattleGridSheetState.Settings -> {
            GenericBottomSheet(
                sheetState = modalSheetState,
                onDismiss = onDismiss,
                content = { SettingsContainer() },
                indicators = { SettingsIndicators() }
            )
        }

        BattleGridSheetState.Restart -> {
            GenericBottomSheet(
                sheetState = modalSheetState,
                onDismiss = onDismiss,
                content = { RestartContainer(
                    onDismiss = {
                        onDismiss.invoke()
                    }
                ) },
                indicators = { RestartIndicators() }
            )
        }

        BattleGridSheetState.DiceRoll -> {
            GenericBottomSheet(
                sheetState = modalSheetState,
                onDismiss = onDismiss,
                content = { DiceRollScreen() },
                indicators = { DiceRollIndicators() }
            )
        }

        BattleGridSheetState.Schemes -> {
            GenericBottomSheet(
                sheetState = modalSheetState,
                onDismiss = onDismiss,
                content = { SchemeContainer(
                    onDismiss = onDismiss
                ) },
                indicators = { SchemeIndicators() }
            )
        }
    }
}