package com.example.diceless.ui.battlegrid.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DicelessDialog(
    onDismiss: () -> Unit = {},
    content: @Composable (onDismiss: () -> Unit) -> Unit
) {

    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            content.invoke { onDismiss.invoke() }

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

//    Dialog(
//        properties = DialogProperties(usePlatformDefaultWidth = false),
//        onDismissRequest = {
//            onDismiss.invoke()
//        }
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.8f)),
//            contentAlignment = Alignment.Center
//        ) {
//            content.invoke { onDismiss.invoke() }
//
//            FloatingActionButton(
//                onClick = { onDismiss.invoke() },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(48.dp)
//                    .align(Alignment.TopEnd),
//                shape = CircleShape
//            ) {
//                Icon(
//                    contentDescription = "",
//                    imageVector = Icons.Default.Close
//                )
//            }
//        }
//    }
}