package com.example.diceless.features.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
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
import com.example.diceless.common.extensions.toFormattedDate
import com.example.diceless.domain.HistoryPlayerBasicData
import com.example.diceless.domain.model.MatchData
import com.example.diceless.features.common.theme.DicelessTheme

@Composable
fun MatchHistoryHeader(
    modifier: Modifier = Modifier,
    header: (@Composable () -> Unit)? = null,
    details: (@Composable () -> Unit)? = null,
    append: (@Composable () -> Unit)? = null
){
    Column(modifier = modifier) {
        header?.let {
            Spacer(modifier = Modifier.height(8.dp))
            header.invoke()
        }

        details?.let {
            Spacer(modifier = Modifier.height(8.dp))
            details.invoke()
        }

        append?.let {
            Spacer(modifier = Modifier.height(8.dp))
            append.invoke()
        }
    }
}



@Composable
fun HistoryDetails(matchData: MatchData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HistoryPlayerQuantityCell(matchData = matchData)

        HistoryStartedAtCell(matchData = matchData)

        HistoryFinishedAtCell(matchData = matchData)
    }
}

@Composable
fun HistoryPlayersHeader(matchData: MatchData){
    Row(modifier = Modifier.fillMaxWidth()) {
        matchData.players.forEach { player ->
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
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
    val matchData = MatchData(
        id = 1,
        players = listOf(
            HistoryPlayerBasicData(name = "Player 1", backgroundImageUri = ""),
            HistoryPlayerBasicData(name = "Player 2", backgroundImageUri = ""),
            HistoryPlayerBasicData(name = "Player 3", backgroundImageUri = ""),
            HistoryPlayerBasicData(name = "Player 4", backgroundImageUri = "")
        )
    )

    MatchHistoryHeader(
        modifier = Modifier
            .background(color = colorScheme.background),
        header = {
            HistoryGameNameCell(matchData = matchData)
        },
        details = {
            HistoryDetails(matchData)
        },
        append = {
            HistoryPlayersHeader(matchData)
        }
    )
}
