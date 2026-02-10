package com.example.diceless.features.common.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.R

@Composable
fun IndicatorPill(
    textColor: Color = Color.LightGray,
    iconColor: Color = Color.LightGray,
    text: String,
    icon: ImageVector
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
                imageVector = icon,
                contentDescription = "Warning",
                tint = iconColor
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Text(
                text = text,
                fontSize = 10.sp,
                color = textColor
            )
        }
    }
}

@Composable
fun RestartIndicatorPill() {
    IndicatorPill(
        text = "REINICIAR",
        icon = Icons.Default.Refresh
    )
}

@Composable
fun LifeValuesIndicatorPill() {
    IndicatorPill(
        text = "DADOS DO JOGO",
        icon = Icons.Default.Favorite
    )
}

@Composable
fun HistoryIndicatorPill() {
    IndicatorPill(
        text = "HISTÓRICO",
        icon = Icons.Default.DateRange
    )
}

@Composable
fun SettingsIndicatorPill() {
    IndicatorPill(
        text = "CONFIGURAÇÕES",
        icon = Icons.Default.Settings
    )
}

@Composable
fun InfoIndicatorPill() {
    IndicatorPill(
        text = "INFORMAÇÕES",
        icon = Icons.Default.Info
    )
}

@Composable
fun UserIndicatorPill() {
    IndicatorPill(
        text = "USUÁRIOS",
        icon = Icons.Default.AccountCircle
    )
}

@Composable
fun DiceRollIndicatorPill() {
    val dice = ImageVector.vectorResource(id = R.drawable.d6_icon)

    IndicatorPill(
        text = "ROLAGEM",
        icon = dice
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewIndicatorPill(){
    RestartIndicatorPill()
}

@Preview(showBackground = true)
@Composable
fun PreviewRestartIndicators(){
    RestartIndicators()
}
