package com.example.diceless.features.history.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryChangeSource
import com.example.diceless.domain.model.MatchHistoryRegistry

@Composable
fun MatchHistoryCell(matchData: MatchData, matchHistoryRegistry: MatchHistoryRegistry){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(matchData.playersCount){ pos ->
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val position = PositionEnum.getPosition(pos + 1)
                val deltaSignal = if (matchHistoryRegistry.delta > 0) "+" else ""
                val textColor = if (matchHistoryRegistry.source == MatchHistoryChangeSource.DAMAGE) Color.Red else Color.Green
                if(position.name == matchHistoryRegistry.playerId){
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = "$deltaSignal${matchHistoryRegistry.delta}",
                        textAlign = TextAlign.End,
                        fontSize = 12.sp,
                        color = textColor
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        text = "${matchHistoryRegistry.lifeAfter}",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "-",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                    )
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
                playersCount = 4
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