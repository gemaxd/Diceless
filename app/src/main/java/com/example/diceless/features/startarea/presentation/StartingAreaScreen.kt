package com.example.diceless.features.startarea.presentation

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.R
import com.example.diceless.common.style.DiceLessTextStyle.BodyText
import com.example.diceless.common.style.DiceLessTextStyle.ButtonText
import com.example.diceless.common.style.DiceLessTextStyle.LogoText
import com.example.diceless.common.style.DiceLessTextStyle.TitleText
import com.example.diceless.features.common.components.DiceLessBackground
import com.example.diceless.features.common.theme.DiceBlack
import com.example.diceless.features.common.theme.DiceDarkSurface
import com.example.diceless.features.common.theme.DiceGraphite
import com.example.diceless.features.common.theme.DiceYellow
import com.example.diceless.features.common.theme.DiceYellowDark
import com.example.diceless.features.common.theme.Purple80
import com.example.diceless.features.common.theme.PurpleGrey40
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.ResultStore
import com.example.diceless.navigation.Route

@Composable
fun StartingAreaScreen(
    resultStore: ResultStore
){
    val navigator = LocalNavigator.current

    StartingAreaContent(
        onContinue = {
            navigator.navigate(
                route = Route.BattleGrid
            )
        },
        onStartNewGame = {
            navigator.navigate(
                route = Route.BattleGrid
            )
        },
        onHistory = {
            navigator.navigate(
                route = Route.HistoryArea
            )
        },
        onOptions = {
            navigator.navigate(
                route = Route.BattleGrid
            )
        }
    )
}

@Composable
fun StartingAreaContent(
    onContinue: () -> Unit = {},
    onStartNewGame: () -> Unit = {},
    onHistory: () -> Unit = {},
    onOptions: () -> Unit = {}
){
    DiceLessBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(5f),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    modifier = Modifier.fillMaxSize().padding(vertical = 32.dp, horizontal = 128.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "DICELESS",
                        textAlign = TextAlign.Center,
                        style = LogoText
                    )

                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = if (isSystemInDarkTheme()) {
                            painterResource(id = R.drawable.diceless_light_icon)
                        } else {
                            painterResource(id = R.drawable.diceless_dark_icon)
                        },
                        contentDescription = "",
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Box(
                modifier = Modifier.weight(5f)
            ) {
                Card(
                    shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(32.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Bem Vindo Novamente",
                                textAlign = TextAlign.Center,
                                style = TitleText
                            )

                            Text(
                                text = "Descrição adicional longa para testar componente",
                                textAlign = TextAlign.Center,
                                style = BodyText
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(3f),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onContinue.invoke()
                                },
                                border = BorderStroke(
                                    width = 4.dp,
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            DiceBlack,
                                            Purple80,
                                            DiceYellowDark,
                                            PurpleGrey40
                                        )
                                    )
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null
                                    )
                                    Spacer(
                                        modifier = Modifier.width(16.dp)
                                    )
                                    Text(
                                        text = "Continue",
                                        style = ButtonText
                                    )
                                }
                            }

                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onStartNewGame.invoke()
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null
                                    )
                                    Spacer(
                                        modifier = Modifier.width(16.dp)
                                    )
                                    Text(
                                        text = "Start a new game",
                                        style = ButtonText
                                    )
                                }
                            }

                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onHistory.invoke()
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.AutoMirrored.Filled.List,
                                        contentDescription = null
                                    )
                                    Spacer(
                                        modifier = Modifier.width(16.dp)
                                    )
                                    Text(
                                        text = "History",
                                        style = ButtonText
                                    )
                                }
                            }

                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onOptions.invoke()
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = null
                                    )
                                    Spacer(
                                        modifier = Modifier.width(16.dp)
                                    )
                                    Text(
                                        text = "Options",
                                        style = ButtonText
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun PreviewStartingAreaScreenDM(){
    StartingAreaContent()
}
