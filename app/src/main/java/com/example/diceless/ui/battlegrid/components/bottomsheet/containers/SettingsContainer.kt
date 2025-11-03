package com.example.diceless.ui.battlegrid.components.bottomsheet.containers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.ui.battlegrid.components.LifeSelectDynamicComponent
import com.example.diceless.ui.battlegrid.components.dialog.GenericDialog
import com.example.diceless.ui.battlegrid.components.groupcomponent.settings.SettingsOption
import com.example.diceless.ui.battlegrid.components.groupcomponent.settings.SettingsOptionFreeContent
import com.example.diceless.ui.battlegrid.components.groupcomponent.settings.SettingsOptionHeader
import com.example.diceless.ui.battlegrid.components.groupcomponent.settings.SettingsOptionToggleControl
import com.example.diceless.ui.battlegrid.components.life.CustomLifeInputter
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.mvi.BattleGridState
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel

@Composable
fun SettingsContainer(
    viewModel: BattleGridViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val onAction = viewModel::onAction

    SettingsContent(
        state = state,
        onAction = onAction
    )
}

@Composable
fun SettingsContent(
    state: BattleGridState,
    onAction: (BattleGridActions) -> Unit
) {
    var showCustomLifeInputter by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        if (showCustomLifeInputter) {
            GenericDialog(
                onDismiss = {
                    showCustomLifeInputter = false
                },
                content = {
                    CustomLifeInputter(
                        currentLife = state.selectedStartingLife.toString(),
                        onConfirmLife = { life ->
                            onAction(BattleGridActions.OnStartingLifeChanged(life.toInt()))
                            showCustomLifeInputter = false
                        }
                    )
                }
            )
        }

        SettingsOption(
            header = {
                SettingsOptionHeader("Valores de vida")
            },
            content = {
                SettingsOptionFreeContent(
                    text = "Escolha um valor de vida padrão",
                    content = {
                        LifeSelectDynamicComponent(
                            selectedLife = state.selectedStartingLife.toString(),
                            staticLifeClick = { life ->
                                onAction(BattleGridActions.OnStartingLifeChanged(life.toInt()))
                            },
                            customLifeClick = {
                                showCustomLifeInputter = true
                            }
                        )
                    }
                )
            }
        )

        HorizontalDivider()

        SettingsOption(
            header = {
                SettingsOptionHeader("Permitir dano a si mesmo")
            },
            content = {
                SettingsOptionToggleControl(
                    isToggled = state.allowSelfCommanderDamage,
                    text = "Esse toggle permite que você marque dano a si mesmo",
                    onClick = {
                        onAction(
                            BattleGridActions.OnAllowSelfCommanderDamageChanged(
                                it
                            )
                        )
                    }
                )
            }
        )

        SettingsOption(
            header = {
                SettingsOptionHeader("Associar dano de commander")
            },
            content = {
                SettingsOptionToggleControl(
                    isToggled = state.linkCommanderDamageToLife,
                    text = "Dano Commander reduz vida, alem de acumular o dano comum.",
                    onClick = {
                        onAction(
                            BattleGridActions.OnLinkCommanderDamageToLifeChanged(
                                it
                            )
                        )
                    }
                )
            }
        )
    }
}
