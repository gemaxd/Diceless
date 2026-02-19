package com.example.diceless.features.startarea.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.R
import com.example.diceless.common.style.DiceLessTextStyle.BodyText
import com.example.diceless.common.style.DiceLessTextStyle.ButtonText
import com.example.diceless.common.style.DiceLessTextStyle.LogoText
import com.example.diceless.common.style.DiceLessTextStyle.TitleText
import com.example.diceless.features.common.theme.Typography
import com.example.diceless.navigation.LocalNavigator
import com.example.diceless.navigation.ResultStore
import com.example.diceless.navigation.Route
import java.util.Locale

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
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFEEEE88), // claro
            Color(0xFFCCCC44), // médio
            Color(0xFFBBBB44)  // escuro
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 2000f)
    )

    Column(
        modifier = Modifier
            .background(
                brush = backgroundGradient
            )
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(5f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(vertical = 32.dp, horizontal = 64.dp),
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
                    painter = painterResource(id = R.drawable.diceless_icon),
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
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCCCC44)
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp
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

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onStartNewGame.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCCCC44)
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp
                            )
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

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onHistory.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCCCC44)
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp
                            )
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

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onOptions.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCCCC44)
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp
                            )
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

@Preview(showBackground = true)
@Composable
fun PreviewStartingAreaScreen(){
    StartingAreaContent()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewStartingAreaScreenDM(){
    StartingAreaContent()
}