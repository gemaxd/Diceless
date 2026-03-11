package com.manarimjesse.diceless.features.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9_PRO_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manarimjesse.diceless.features.common.theme.DiceBlack
import com.manarimjesse.diceless.features.common.theme.DiceGraphite
import com.manarimjesse.diceless.features.common.theme.DiceWhite
import com.manarimjesse.diceless.features.common.theme.DiceYellow
import com.manarimjesse.diceless.features.common.theme.DiceYellowDark

@Composable
fun DiceLessBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    if (isSystemInDarkTheme()){
        DarkDiceLessBackground(
            modifier = modifier,
            content = content
        )
    } else {
        LightDiceLessBackground(
            modifier = modifier,
            content = content
        )
    }
}

@Composable
fun DarkDiceLessBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
){
    Box(modifier = modifier.fillMaxSize()) {

        // 🔥 Base
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(DiceGraphite)
        )

        // 🌕 Blob Amarelo
        Box(
            modifier = Modifier
                .size(500.dp)
                .offset(x = (-120).dp, y = (-80).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            DiceYellowDark.copy(0.3f), // amarelo com alpha
                            Color.Transparent
                        )
                    )
                )
                .blur(180.dp)
        )

        // 🌑 Blob Grafite
        Box(
            modifier = Modifier
                .size(600.dp)
                .offset(x = 200.dp, y = 300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0x441C1C1E),
                            Color.Transparent
                        )
                    )
                )
                .blur(450.dp)
        )

        // ✨ Overlay para profundidade
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            DiceBlack.copy(alpha = 0.5f)
                        )
                    )
                )
        )

        // 📦 Conteúdo
        content()
    }
}

@Composable
fun LightDiceLessBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
){
    Box(modifier = modifier.fillMaxSize()) {

        // 🔥 Base
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(DiceYellow)
        )

        // 🌕 Blob Amarelo
        Box(
            modifier = Modifier
                .size(500.dp)
                .offset(x = (-120).dp, y = (-80).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            DiceWhite.copy(0.3f), // amarelo com alpha
                            Color.Transparent
                        )
                    )
                )
                .blur(180.dp)
        )

        // 🌑 Blob Grafite
        Box(
            modifier = Modifier
                .size(600.dp)
                .offset(x = 200.dp, y = 300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            DiceWhite.copy(0.3f),
                            Color.Transparent
                        )
                    )
                )
                .blur(450.dp)
        )

        // ✨ Overlay para profundidade
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            DiceWhite.copy(alpha = 0.5f)
                        )
                    )
                )
        )

        // 📦 Conteúdo
        content()
    }
}

@Preview(device = PIXEL_9_PRO_XL)
@Composable
fun PreviewBackGround(){
    DiceLessBackground(
        modifier = Modifier.fillMaxSize(),
        content = {}
    )
}