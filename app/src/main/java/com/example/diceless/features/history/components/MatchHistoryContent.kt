package com.example.diceless.features.history.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryRegistry

@Composable
fun MatchHistoryContent(
    modifier: Modifier = Modifier,
    matchData: MatchData?,
    histories: List<MatchHistoryRegistry>
){
    LazyColumn(modifier = modifier.padding(horizontal = 8.dp)) {
        matchData?.let {
            stickyHeader {
                MatchHistoryHeader(
                    header = {
                        HistoryGameNameCell(matchData = matchData)
                    },
                    details = {
                        HistoryDetails(matchData = matchData)
                    },
                    append = {
                        HistoryPlayersHeader(matchData = matchData)
                    }
                )
            }
            items(items = histories){ history ->
                MatchHistoryChanges(matchData = matchData, matchHistoryRegistry = history)
                MatchHistoryRow(matchData = matchData, matchHistoryRegistry = history)
            }
        }
    }
}