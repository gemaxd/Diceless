package com.example.diceless.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.vector.ImageVector

data class CounterData(
    val id: String,
    var icon: ImageVector,
    var toggleValue: Boolean?,
    var value: Int?,
    var isSelected: Boolean = false
){
    fun isToggle() = this.toggleValue != null
}

fun getDefaultCounterData() =
    mutableStateListOf(
        CounterData(id = "first", icon = Icons.Filled.AccountBox, toggleValue = true, value = 1, isSelected = true),
        CounterData(id = "second", icon = Icons.Filled.Add, toggleValue = true, value = 1),
        CounterData(id = "third", icon = Icons.Filled.AccountCircle, toggleValue = true, value = 1),
        CounterData(id = "forth", icon = Icons.Filled.Call, toggleValue = true, value = 1),
        CounterData(id = "fifth", icon = Icons.Filled.AddCircle, toggleValue = true, value = 1),
        CounterData(id = "sixth", icon = Icons.Filled.ArrowDropDown, toggleValue = true, value = 1),
        CounterData(id = "seventh", icon = Icons.Filled.Create, toggleValue = true, value = 1),
        CounterData(id = "eighth", icon = Icons.Filled.Done, toggleValue = true, value = 1),
        CounterData(id = "nineth", icon = Icons.Filled.DateRange, toggleValue = true, value = 1),
        CounterData(id = "tenth", icon = Icons.Filled.Build, toggleValue = true, value = 1),
        CounterData(id = "eleventh", icon = Icons.Filled.Face, toggleValue = true, value = 1),
    )

