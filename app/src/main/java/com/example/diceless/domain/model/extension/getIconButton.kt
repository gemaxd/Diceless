package com.example.diceless.domain.model.extension

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.MenuItemEnum
import com.example.diceless.domain.model.MenuItem

fun MenuItem.getIconButton() =
    when(this.menuItemEnum){
        MenuItemEnum.HISTORY -> {
            this.apply {
                icon = {
                    IconButton(onClick = { this.action.invoke() }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        MenuItemEnum.SETTINGS -> {
            this.apply {
                icon = {
                    IconButton(onClick = { action.invoke() }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        MenuItemEnum.RESTART -> {
            this.apply {
                icon = {
                    IconButton(onClick = { action.invoke() }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        MenuItemEnum.SCHEMES -> {
            this.apply {
                icon = {
                    IconButton(onClick = { action.invoke() }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Face,
                            contentDescription = "Refresh",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }