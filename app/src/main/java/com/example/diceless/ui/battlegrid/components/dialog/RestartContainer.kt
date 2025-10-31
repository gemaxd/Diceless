package com.example.diceless.ui.battlegrid.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diceless.ui.battlegrid.mvi.BattleGridActions
import com.example.diceless.ui.battlegrid.viewmodel.BattleGridViewModel

@Composable
fun RestartContainer(
    viewModel: BattleGridViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
){
    val onAction = viewModel::onAction

    RestartContent(
        onAction = onAction,
        onDismiss = onDismiss
    )
}

@Composable
fun RestartContent(
    onAction: (BattleGridActions) -> Unit,
    onDismiss: () -> Unit
){
    Column {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RestartContentHeader()

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            RestartContentBody(
                onConfirm = {
                    onDismiss.invoke()
                    onAction(BattleGridActions.OnRestart)
                }
            )
        }
    }
}

@Composable
fun RestartContentHeader(){
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(all = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = CardDefaults.outlinedCardBorder(
                    enabled = true
                )
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Warning"
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                    Text(
                        text = "REINICIAR",
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = CardDefaults.outlinedCardBorder(
                    enabled = true
                )
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Warning"
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                    Text(
                        text = "DADOS DO JOGO",
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RestartContentBody(
    onConfirm: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.weight(1f),
            imageVector = Icons.Default.Refresh,
            contentDescription = ""
        )

        Column(
            modifier = Modifier.weight(5f),
        ) {
            Text(
                text = "Reiniciando",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = "Reiniciar a partida apaga todo o progresso!",
                fontSize = 12.sp
            )
        }

        TextButton(
            modifier = Modifier.weight(1f),
            onClick = {
                onConfirm.invoke()
            }
        ) {
            Text("OK")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRestartContent(){
    RestartContent(
        onAction = {},
        onDismiss = {}
    )
}
