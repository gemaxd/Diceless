package com.example.diceless.features.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.extensions.toFormattedDate
import com.example.diceless.domain.model.MatchData

@Composable
fun MatchHistoryHeader(matchData: MatchData){
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Game #${matchData.id}",
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )

            Text(
                text = "Started at:",
                textAlign = TextAlign.End
            )

            Text(
                text = matchData.createdAt.toFormattedDate(),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Medium
            )
        }


        Spacer(modifier = Modifier.size(12.dp))

        Row(modifier = Modifier.fillMaxWidth().background(Color.Gray)) {
            repeat(matchData.playersCount){ position ->
                val positionEnum = PositionEnum.getPosition(position + 1)
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    text = "Player #${positionEnum.pos}",
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMatchHistoryHeader(){
    MatchHistoryHeader(matchData = MatchData(id = 1, playersCount = 4))
}
