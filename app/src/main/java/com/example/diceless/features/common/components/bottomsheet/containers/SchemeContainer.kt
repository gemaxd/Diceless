package com.example.diceless.features.common.components.bottomsheet.containers


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
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
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.features.battlegrid.mvi.BattleGridActions
import com.example.diceless.features.battlegrid.viewmodel.BattleGridViewModel
import com.example.diceless.presentation.battlegrid.components.groupcomponent.scheme.SchemeView

@Composable
fun SchemeContainer(
    viewModel: BattleGridViewModel = hiltViewModel(),
    onDismiss: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val selectedScheme = uiState.selectedScheme
    val onAction = viewModel::onAction

    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Selecione um esquema",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Escolha um esquema, entre os disponíveis para jogar",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.padding(4.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            item {
                SchemeView(
                    selectedScheme = selectedScheme,
                    schemeEnum = SchemeEnum.SOLO,
                    onSelectScheme = { scheme ->
                        onAction(BattleGridActions.OnUpdateScheme(scheme))
                        onDismiss.invoke()
                    }
                )
            }

            item {
                SchemeView(
                    selectedScheme = selectedScheme,
                    schemeEnum =SchemeEnum.VERSUS_OPPOSITE,
                    onSelectScheme = { scheme ->
                        onAction(BattleGridActions.OnUpdateScheme(scheme))
                        onDismiss.invoke()
                    }
                )
            }

            item {
                SchemeView(
                    selectedScheme = selectedScheme,
                    schemeEnum =SchemeEnum.TRIPLE_STANDARD,
                    onSelectScheme = { scheme ->
                        onAction(BattleGridActions.OnUpdateScheme(scheme))
                        onDismiss.invoke()
                    }
                )
            }

            item {
                SchemeView(
                    selectedScheme = selectedScheme,
                    schemeEnum =SchemeEnum.QUADRA_STANDARD,
                    onSelectScheme = { scheme ->
                        onAction(BattleGridActions.OnUpdateScheme(scheme))
                        onDismiss.invoke()
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSchemeContainer() {
    SchemeContainer(
        onDismiss = {}
    )
}