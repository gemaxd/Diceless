package com.manarimjesse.diceless.app.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("Navigator não fornecido")
}
