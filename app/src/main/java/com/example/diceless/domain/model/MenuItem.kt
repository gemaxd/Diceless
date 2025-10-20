package com.example.diceless.domain.model

import androidx.compose.runtime.Composable
import com.example.diceless.common.enums.MenuItemEnum

data class MenuItem(
    var icon: @Composable () -> Unit = {},
    val action: () -> Unit,
    val menuItemEnum: MenuItemEnum
)
