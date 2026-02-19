package com.example.diceless.features.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.diceless.common.extensions.toFormattedDate
import com.example.diceless.domain.model.MatchData

@Composable
fun HistoryGameNameCell(
    modifier: Modifier = Modifier,
    matchData: MatchData
){
    Column(modifier = modifier) {
        Text(
            text = "Game Name",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = "Game #${matchData.id}",
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
fun HistoryPlayerQuantityCell(
    modifier: Modifier = Modifier,
    matchData: MatchData
){
    Column(modifier = modifier) {
        Text(
            text = "Players",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = "${matchData.players.size}",
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
    }
}

@Composable
fun HistoryStartedAtCell(
    modifier: Modifier = Modifier,
    matchData: MatchData
) {
    Column(modifier = modifier) {
        Text(
            text = "Started at:",
            textAlign = TextAlign.End,
            color = Color.Gray
        )

        Text(
            text = matchData.createdAt.toFormattedDate(),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
fun HistoryFinishedAtCell(
    modifier: Modifier = Modifier,
    matchData: MatchData
) {
    matchData.finishedAt?.let { finishedAt ->
        Column(modifier = modifier) {
            Text(
                text = "Finished at:",
                textAlign = TextAlign.End,
                color = Color.Gray
            )

            Text(
                text = finishedAt.toFormattedDate(),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}