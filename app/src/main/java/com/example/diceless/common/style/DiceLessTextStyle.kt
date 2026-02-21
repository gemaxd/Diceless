package com.example.diceless.common.style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.diceless.features.common.theme.BarlowFont

object DiceLessTextStyle {
    val ButtonText = TextStyle(
        fontFamily = BarlowFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = (0).sp
    )

    val TitleText = TextStyle(
        fontFamily = BarlowFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = (-0.5).sp
    )

    val LogoText = TextStyle(
        fontFamily = BarlowFont,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        letterSpacing = (-0.25).sp
    )

    val BodyText = TextStyle(
        fontFamily = BarlowFont,
        fontSize = 16.sp,
        letterSpacing = (-0.5).sp
    )

}