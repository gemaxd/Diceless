package com.example.diceless.presentation.battlegrid.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.CounterIconType
import com.example.diceless.domain.model.toImageVector

@Composable
fun CounterPill(counterData: CounterData){
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.15f)
        )
    ) {
        Row (
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(2.dp)
                    .size(18.dp),
                contentDescription = null,
                imageVector = counterData.iconType.toImageVector(),
                tint = Color.Gray.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.padding(2.dp))

            counterData.value?.let { value ->
                if (value > 0){
                    Text(
                        text = value.toString(),
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPillPreview(){
    CounterPill(
        counterData = CounterData(
            iconType = CounterIconType.BUILD,
            id = "",
            value = 2
        )
    )
}