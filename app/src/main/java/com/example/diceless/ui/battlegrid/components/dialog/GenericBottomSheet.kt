package com.example.diceless.ui.battlegrid.components.dialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    content: @Composable (dismiss: () -> Unit) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        tonalElevation = 8.dp,
        dragHandle = {}
    ) {
        content { onDismiss() }
    }
}