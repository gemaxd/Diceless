package com.example.diceless.ui.battlegrid.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun RestartDialog(
    onRestart: () -> Unit = {},
    onDismiss: () -> Unit = {}
){
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss.invoke()
        }
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = 0.8f))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = Color.White,
                    text = "Deseja reiniciar?",
                    fontSize = 25.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { onDismiss.invoke() }
                    ) {
                        Text(
                            text = "Não",
                            fontSize = 20.sp,
                            color = Color.Red
                        )
                    }

                    TextButton(
                        onClick = {
                            onDismiss.invoke()
                            onRestart.invoke()
                        }
                    ) {
                        Text(
                            text = "Sim",
                            fontSize = 20.sp,
                            color = Color.Green
                        )
                    }
                }
            }
        }
    }
}