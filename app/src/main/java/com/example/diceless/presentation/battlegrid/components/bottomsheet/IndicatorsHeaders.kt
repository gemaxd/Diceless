package com.example.diceless.presentation.battlegrid.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RestartIndicators(){
    Row(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        RestartIndicatorPill()
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        LifeValuesIndicatorPill()
    }
}

@Composable
fun HistoryIndicators(){
    Row(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        HistoryIndicatorPill()
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        LifeValuesIndicatorPill()
    }
}

@Composable
fun SettingsIndicators(){
    Row(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SettingsIndicatorPill()
    }
}

@Composable
fun SchemeIndicators(){
    Row(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        UserIndicatorPill()
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        InfoIndicatorPill()
    }
}
