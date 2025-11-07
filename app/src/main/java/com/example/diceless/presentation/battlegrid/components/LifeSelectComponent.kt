package com.example.diceless.presentation.battlegrid.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.ChosenLifeEnum

@Composable
fun LifeSelectStaticComponent(
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    text: String,
    onClickLife: () -> Unit
){
    Card(
        modifier = modifier.clickable(
            onClick = { onClickLife.invoke() }
        ),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(isSelected) {
                Color.Black
            } else {
                androidx.compose.material3.MaterialTheme.colorScheme.surface
            },
            contentColor = if(isSelected) {
                Color.Yellow
            } else {
                Color.Black
            }
        )
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LifeSelectDynamicComponent(
    selectedLife: String = "99",
    staticLifeClick: (String) -> Unit = {},
    customLifeClick: () -> Unit = {}
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        LifeSelectStaticComponent(
            isSelected = (ChosenLifeEnum.TWENTY_LIFE.textLife == selectedLife),
            modifier = Modifier.padding(8.dp),
            text = ChosenLifeEnum.TWENTY_LIFE.textLife,
            onClickLife = {
                staticLifeClick(ChosenLifeEnum.TWENTY_LIFE.textLife)
            }
        )
        LifeSelectStaticComponent(
            isSelected = (ChosenLifeEnum.THIRTY_LIFE.textLife == selectedLife),
            modifier = Modifier.padding(8.dp),
            text = ChosenLifeEnum.THIRTY_LIFE.textLife,
            onClickLife = {
                staticLifeClick(ChosenLifeEnum.THIRTY_LIFE.textLife)
            }
        )
        LifeSelectStaticComponent(
            isSelected = (ChosenLifeEnum.FOURTY_LIFE.textLife == selectedLife),
            modifier = Modifier.padding(8.dp),
            text = ChosenLifeEnum.FOURTY_LIFE.textLife,
            onClickLife = {
                staticLifeClick(ChosenLifeEnum.FOURTY_LIFE.textLife)
            }
        )
        LifeSelectStaticComponent(
            isSelected = ChosenLifeEnum.isCustomLifeValid(selectedLife),
            modifier = Modifier.padding(8.dp),
            text = if (ChosenLifeEnum.contains(selectedLife)) { "??" } else { selectedLife },
            onClickLife = {
                customLifeClick.invoke()
            }
        )
    }
}

@Preview
@Composable
fun PreviewLifeSelectStaticComponent(){
    LifeSelectStaticComponent(
        modifier = Modifier.size(32.dp),
        text = "20",
        onClickLife = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLifeSelectDynamicComponent(){
    LifeSelectDynamicComponent()
}
