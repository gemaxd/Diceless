package com.example.diceless.features.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.extensions.toFormattedDate
import com.example.diceless.domain.HistoryPlayerBasicData
import com.example.diceless.domain.model.MatchData

@Composable
fun MatchHistoryHeader(matchData: MatchData){
    Column(modifier = Modifier.fillMaxWidth().background(Color.Black)) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column {
                Text(
                    text = "Game Name",
                    fontSize = 12.sp,
                    color = Color.White
                )

                Text(
                    text = "Game #${matchData.id}",
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    color = Color.White
                )
            }




            Text(
                text = "Started at:",
                textAlign = TextAlign.End,
                color = Color.White
            )

            Text(
                text = matchData.createdAt.toFormattedDate(),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }


        Spacer(modifier = Modifier.size(12.dp))

        Row(modifier = Modifier.fillMaxWidth().background(Color.Gray)) {

            matchData.players.forEach { player ->
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImage(
                        model = player.backgroundImageUri,
                        contentDescription = player.backgroundImageUri,
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "P${player.playerPosition.pos}",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMatchHistoryHeader(){
    MatchHistoryHeader(
        matchData = MatchData(
            id = 1,
            players = listOf(
                HistoryPlayerBasicData(name = "Player 1", backgroundImageUri = ""),
                HistoryPlayerBasicData(name = "Player 2", backgroundImageUri = ""),
                HistoryPlayerBasicData(name = "Player 3", backgroundImageUri = ""),
                HistoryPlayerBasicData(name = "Player 4", backgroundImageUri = "")
            )
        )
    )
}
