package com.example.diceless.features.history.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.domain.HistoryPlayerBasicData
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryChangeSource
import com.example.diceless.domain.model.MatchHistoryRegistry

@Composable
fun MatchHistoryCell(matchData: MatchData, matchHistoryRegistry: MatchHistoryRegistry){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        matchData.players.forEach { player ->
            Box(
                modifier = Modifier.weight(1f)
            ) {
                val deltaSignal = if (matchHistoryRegistry.delta > 0) "+" else ""
                val containerColor = if (matchHistoryRegistry.source == MatchHistoryChangeSource.DAMAGE) Color.Red else Color.Blue
                val textColor = if (matchHistoryRegistry.source == MatchHistoryChangeSource.DAMAGE) Color.Red else Color.Green
                if(player.playerPosition.name == matchHistoryRegistry.playerId){
                    Card(
                        modifier = Modifier.padding(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = containerColor.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(percent = 10)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()){
                            Text(
                                modifier = Modifier.align(Alignment.CenterStart).padding(start = 12.dp),
                                text = "$deltaSignal${matchHistoryRegistry.delta}",
                                textAlign = TextAlign.End,
                                fontSize = 16.sp,
                                color = textColor
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                text = "${matchHistoryRegistry.lifeAfter}",
                                textAlign = TextAlign.Center,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                } else {
                    Card(
                        modifier = Modifier.padding(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Gray.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(percent = 10)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            text = "",
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMatchHistoryCell(){
    Column() {
        MatchHistoryCell(
            matchData = MatchData(
                id = 1,
                players = listOf(
                    HistoryPlayerBasicData(name = "Player 1", backgroundImageUri = ""),
                    HistoryPlayerBasicData(name = "Player 2", backgroundImageUri = ""),
                    HistoryPlayerBasicData(name = "Player 3", backgroundImageUri = ""),
                    HistoryPlayerBasicData(name = "Player 4", backgroundImageUri = "")
                )
            ),
            matchHistoryRegistry = MatchHistoryRegistry(
                id = 1,
                matchId = 1,
                playerId = "PLAYER_ONE",
                delta = 1,
                lifeBefore = 1,
                lifeAfter = 40,
                timestamp = 1,
                source = MatchHistoryChangeSource.DAMAGE
            )
        )
    }
}