package com.example.diceless.ui.battlegrid.components.groupcomponent.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.ui.battlegrid.components.bottomsheet.containers.SettingsContent
import com.example.diceless.ui.battlegrid.mvi.BattleGridState


@Composable
fun SettingsOption(
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
){
    Column(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        header()
        content()
    }
}

@Composable
fun SettingsOptionHeader(text: String){
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun SettingsOptionToggleControl(
    isToggled: Boolean,
    text: String,
    onClick: (Boolean) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Switch(
            checked = isToggled,
            onCheckedChange = {
                onClick.invoke(it)
            }
        )
    }
}

@Composable
fun SettingsOptionFreeContent(
    text: String = "",
    content: @Composable () -> Unit = {}

){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.padding(2.dp))

        content.invoke()
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSettingsContent() {
    SettingsContent(
        state = BattleGridState(
            players = emptyList()
        ),
        onAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsOptionToggleControl() {
    SettingsOptionToggleControl(
        isToggled = true,
        text = "Esse toggle permite que você marque dano a si mesmo",
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsOption() {
    SettingsOption(
        header = {
            SettingsOptionHeader("Permitir dano a si mesmo")
        },
        content = {
            SettingsOptionToggleControl(
                isToggled = true,
                text = "Esse toggle permite que você marque dano a si mesmo",
                onClick = {}
            )
        },

        )
}