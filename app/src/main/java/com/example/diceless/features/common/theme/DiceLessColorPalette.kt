package com.example.diceless.features.common.theme

import androidx.compose.ui.graphics.Color

data class DiceLessButtonColors(
    val containerColor: Color,
    val textColor: Color
)

val LightDiceLessButtonColors = DiceLessButtonColors(
    containerColor = Color(0xFF0055FF),
    textColor = Color(0xFF000000)
)

val DarkDiceLessButtonColors = DiceLessButtonColors(
    containerColor = Color(0xFF000000),
    textColor = Color(0xFF0055FF)
)
