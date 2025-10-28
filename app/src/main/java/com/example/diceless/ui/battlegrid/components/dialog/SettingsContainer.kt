package com.example.diceless.ui.battlegrid.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.mvi.BattleGridState
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel

@Composable
fun SettingsContainer(
    viewModel: BattleGridViewModel = hiltViewModel(),
    onDismiss: () -> Unit
){
    val state by viewModel.state.collectAsState()
    val onAction = viewModel::onAction

    SettingsContent(
        state = state,
        onAction = onAction,
        onDismiss = onDismiss
    )
}

@Composable
fun SettingsContent(
    state: BattleGridState,
    onAction: (BattleGridActions) -> Unit,
    onDismiss: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Vida inicial
            OutlinedTextField(
                value = state.selectedStartingLife.toString(),
                onValueChange = { text ->
                    text.toIntOrNull()
                        ?.let { onAction(BattleGridActions.OnStartingLifeChanged(it)) }
                },
                label = { Text("Vida Inicial") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                )
            )

            // Switches
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Permitir dano a si mesmo", color = Color.White)
                Switch(
                    checked = state.allowSelfCommanderDamage,
                    onCheckedChange = {
                        onAction(
                            BattleGridActions.OnAllowSelfCommanderDamageChanged(
                                it
                            )
                        )
                    }
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Dano Commander reduz vida", color = Color.White)
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = state.linkCommanderDamageToLife,
                    onCheckedChange = {
                        onAction(
                            BattleGridActions.OnLinkCommanderDamageToLifeChanged(
                                it
                            )
                        )
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { onDismiss.invoke() },
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp)
                .align(Alignment.TopEnd),
            shape = CircleShape
        ) {
            Icon(
                contentDescription = "",
                imageVector = Icons.Default.Close
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsContent() {
    SettingsContent(
        state = BattleGridState(
            players = emptyList()
        ),
        onDismiss = {},
        onAction = {}
    )
}