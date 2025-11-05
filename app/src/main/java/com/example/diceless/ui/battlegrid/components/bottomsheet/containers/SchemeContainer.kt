package com.example.diceless.ui.battlegrid.components.bottomsheet.containers


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.ui.battlegrid.components.groupcomponent.scheme.SchemeView

@Composable
fun SchemeContainer(
    selectedScheme: SchemeEnum = SchemeEnum.SOLO,
    onSelectScheme: (SchemeEnum) -> Unit
){
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
                        onSelectScheme(scheme)
                    }
                )
            }

            item {
                SchemeView(
                    selectedScheme = selectedScheme,
                    schemeEnum =SchemeEnum.VERSUS_OPPOSITE,
                    onSelectScheme = { scheme ->
                        onSelectScheme(scheme)
                    }
                )
            }

            item {
                SchemeView(
                    selectedScheme = selectedScheme,
                    schemeEnum =SchemeEnum.TRIPLE_STANDARD,
                    onSelectScheme = { scheme ->
                        onSelectScheme(scheme)
                    }
                )
            }

            item {
                SchemeView(
                    selectedScheme = selectedScheme,
                    schemeEnum =SchemeEnum.QUADRA_STANDARD,
                    onSelectScheme = { scheme ->
                        onSelectScheme(scheme)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSchemeContainer() {
    SchemeContainer(onSelectScheme = {})
}