package com.example.diceless.features.history.components

import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.domain.model.MatchData
import com.example.diceless.domain.model.MatchHistoryRegistry
import kotlin.random.Random

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
                    modifier = Modifier
                        .background(color = colorScheme.background),
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


@Composable
fun ParticleGlowOverlay(
    modifier: Modifier = Modifier,
    particleCount: Int = 40
) {
    Canvas(modifier = modifier) {

        val width = size.width
        val height = size.height

        repeat(particleCount) {
            val x = Random.nextFloat() * width
            val y = Random.nextFloat() * height * 0.6f // concentra no topo
            val radius = Random.nextFloat() * 6f + 2f
            val alpha = Random.nextFloat() * 0.4f + 0.1f

            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = radius,
                center = Offset(x, y)
            )

            drawLine(
                color = Color.White.copy(alpha = 0.15f),
                start = Offset(x, y),
                end = Offset(x + 30f, y),
                strokeWidth = 1f
            )

            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = radius,
                center = Offset(x, y),
                blendMode = BlendMode.Plus
            )
        }
    }
}

@Composable
fun GlowCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1F2B),
                        Color(0xFF0F1218)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0x66FFD700),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )

    ) {

        ParticleGlowOverlay(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = android.graphics.RenderEffect
                            .createBlurEffect(
                                5f,
                                5f,
                                android.graphics.Shader.TileMode.CLAMP
                            ).asComposeRenderEffect()
                    }
                }
        )


        // Conteúdo do card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Game #6",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "22 Feb 2026 • Duration: 24m 13s",
                color = Color.LightGray
            )
        }

        // ✨ Glow superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.35f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Preview
@Composable
fun PreviewGlowCard(){
    GlowCard()
}