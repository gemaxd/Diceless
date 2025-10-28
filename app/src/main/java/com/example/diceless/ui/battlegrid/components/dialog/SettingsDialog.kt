package com.example.diceless.ui.battlegrid.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit = {}
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss.invoke()
        }
    ) {
        SettingsContainer(
            onDismiss = onDismiss
        )
    }
}