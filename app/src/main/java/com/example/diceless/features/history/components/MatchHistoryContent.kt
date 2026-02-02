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
fun MatchHistoryContent(matchData: MatchData?, histories: List<MatchHistoryRegistry>){
    LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
        matchData?.let {
            stickyHeader {
                MatchHistoryHeader(matchData = matchData)
            }
            items(items = histories){ history ->
                MatchHistoryCell(matchData = matchData, matchHistoryRegistry = history)
            }
        }
    }
}