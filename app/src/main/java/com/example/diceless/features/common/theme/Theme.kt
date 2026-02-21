package com.example.diceless.features.common.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DiceYellowDark,
    onPrimary = DiceBlack,
    secondary = DiceYellow,
    onSecondary = DiceBlack,
    background = DiceBlack,
    onBackground = DiceWhite,
    surface = DiceGraphite,
    onSurface = DiceWhite,
    primaryContainer = DiceYellow.copy(alpha = 0.20f),
    onPrimaryContainer = DiceWhite,
    surfaceVariant = DiceDarkSurface,
    onSurfaceVariant = DiceWhite.copy(alpha = 0.7f)
)

private val LightColorScheme = lightColorScheme(
    primary = DiceYellow,
    onPrimary = DiceBlack,
    secondary = DiceGraphite,
    onSecondary = DiceWhite,
    background = DiceWhite,
    onBackground = DiceBlack,
    surface = DiceWhite,
    onSurface = DiceBlack,
    primaryContainer = DiceYellow.copy(alpha = 0.15f),
    onPrimaryContainer = DiceBlack,
    surfaceVariant = DiceGraphite.copy(alpha = 0.08f),
    onSurfaceVariant = DiceGraphite
)

@Composable
fun DicelessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

