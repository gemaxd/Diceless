package com.example.diceless.presentation.battlegrid.components.dialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericDialog(
    onDismiss: () -> Unit,
    content: @Composable (dismiss: () -> Unit) -> Unit
) {
    Dialog (
        onDismissRequest = onDismiss
    ) {
        content { onDismiss() }
    }
}