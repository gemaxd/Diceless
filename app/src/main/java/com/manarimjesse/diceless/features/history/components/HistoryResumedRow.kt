package com.manarimjesse.diceless.features.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manarimjesse.diceless.domain.model.MatchData

@Composable
fun HistoryResumedRow(
    matchData: MatchData
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        HistoryGameNameCell(matchData = matchData)
        HistoryPlayerQuantityCell(matchData = matchData)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HistoryStartedAtCell(matchData = matchData)
            HistoryFinishedAtCell(matchData = matchData)
        }
    }
}