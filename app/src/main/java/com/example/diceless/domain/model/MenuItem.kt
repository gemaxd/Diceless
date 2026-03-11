package com.example.diceless.domain.model

import androidx.compose.runtime.Composable
import com.example.diceless.domain.model.enums.MenuItemEnum

data class MenuItem(
    var icon: @Composable () -> Unit = {},
    val action: () -> Unit,
    val menuItemEnum: MenuItemEnum
)
