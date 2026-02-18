package com.example.diceless.features.startarea.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.ResultStore
import com.example.diceless.navigation.Route

@Composable
fun StartingAreaScreen(
    resultStore: ResultStore
){
    StartingAreaContent()
}

@Composable
fun StartingAreaContent(){
    val navigator = LocalNavigator.current

    Column {

        Button(
            onClick = {
                navigator.navigate(
                    route = Route.BattleGrid
                )
            },
        ) {
            Text(
                text = "Continue"
            )
        }

        Button(
            onClick = {
                navigator.navigate(
                    route = Route.BattleGrid
                )
            },
        ) {
            Text(
                text = "Start a new game"
            )
        }

        Button(
            onClick = {
                navigator.navigate(
                    route = Route.HistoryArea
                )
            },
        ) {
            Text(
                text = "History"
            )
        }

        Button(
            onClick = {
                navigator.navigate(
                    route = Route.BattleGrid
                )
            },
        ) {
            Text(
                text = "Options"
            )
        }

    }
}