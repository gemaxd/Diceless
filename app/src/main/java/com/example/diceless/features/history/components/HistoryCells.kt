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
import com.example.diceless.common.style.DiceLessTextStyle.CellLabel
import com.example.diceless.common.style.DiceLessTextStyle.CellValue
import com.example.diceless.domain.model.MatchData

@Composable
fun HistoryGameNameCell(
    modifier: Modifier = Modifier,
    matchData: MatchData
){
    Column(modifier = modifier) {
        Text(
            text = "Game Name",
            style = CellLabel
        )

        Text(
            text = "Game #${matchData.id}",
            style = CellValue
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
            style = CellLabel
        )

        Text(
            text = "${matchData.players.size}",
            style = CellValue
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
            style = CellLabel
        )

        Text(
            text = matchData.createdAt.toFormattedDate(),
            textAlign = TextAlign.End,
            style = CellValue
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
                style = CellLabel
            )

            Text(
                text = finishedAt.toFormattedDate(),
                textAlign = TextAlign.End,
                style = CellValue
            )
        }
    }
}