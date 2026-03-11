package com.manarimjesse.diceless.features.middlemenu.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.manarimjesse.diceless.features.bottombelt.options.diceroll.presentation.DiceRollScreen
import com.manarimjesse.diceless.features.common.components.bottomsheet.DiceRollIndicators
import com.manarimjesse.diceless.features.common.components.bottomsheet.GenericBottomSheet
import com.manarimjesse.diceless.features.common.components.bottomsheet.HistoryIndicators
import com.manarimjesse.diceless.features.common.components.bottomsheet.RestartIndicators
import com.manarimjesse.diceless.features.common.components.bottomsheet.SchemeIndicators
import com.manarimjesse.diceless.features.common.components.bottomsheet.SettingsIndicators
import com.manarimjesse.diceless.features.common.components.bottomsheet.containers.RestartContainer
import com.manarimjesse.diceless.features.common.components.bottomsheet.containers.SchemeContainer
import com.manarimjesse.diceless.features.history.components.MatchHistoryContainer
import com.manarimjesse.diceless.features.middlemenu.mvi.BattleGridSheetState
import com.manarimjesse.diceless.presentation.battlegrid.components.bottomsheet.containers.SettingsContainer

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