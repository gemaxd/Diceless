package com.manarimjesse.diceless.features.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.manarimjesse.diceless.core.extensions.toFormattedDate
import com.manarimjesse.diceless.core.designsystem.text.DiceLessTextStyle.CellLabel
import com.manarimjesse.diceless.core.designsystem.text.DiceLessTextStyle.CellValue
import com.manarimjesse.diceless.domain.model.MatchData

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