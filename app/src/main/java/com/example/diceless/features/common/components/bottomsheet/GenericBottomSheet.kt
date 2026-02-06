package com.example.diceless.features.common.components.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@ExperimentalMaterial3Api
@Composable
fun GenericBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    content: @Composable (dismiss: () -> Unit) -> Unit,
    indicators: @Composable () -> Unit = {},
    containerColor: Color = Color.White
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = containerColor,
        tonalElevation = 8.dp,
        dragHandle = {}
    ) {
        indicators.invoke()

        content { onDismiss() }
    }
}