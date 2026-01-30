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
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class CounterData(
    val id: String,
    val iconType: CounterIconType,
    var toggleValue: Boolean? = null,
    var value: Int? = null,
    var isSelected: Boolean = false,
    var isCritical: Boolean = false
){
    fun isToggle() = this.toggleValue != null
}

fun CounterIconType.toImageVector(): ImageVector =
    when (this) {
        CounterIconType.ACCOUNT_BOX -> Icons.Filled.AccountBox
        CounterIconType.ADD -> Icons.Filled.Add
        CounterIconType.ACCOUNT_CIRCLE -> Icons.Filled.AccountCircle
        CounterIconType.CALL -> Icons.Filled.Call
        CounterIconType.ADD_CIRCLE -> Icons.Filled.AddCircle
        CounterIconType.ARROW_DROP_DOWN -> Icons.Filled.ArrowDropDown
        CounterIconType.CREATE -> Icons.Filled.Create
        CounterIconType.DONE -> Icons.Filled.Done
        CounterIconType.DATE_RANGE -> Icons.Filled.DateRange
        CounterIconType.BUILD -> Icons.Filled.Build
        CounterIconType.FACE -> Icons.Filled.Face
    }

enum class CounterIconType {
    ACCOUNT_BOX,
    ADD,
    ACCOUNT_CIRCLE,
    CALL,
    ADD_CIRCLE,
    ARROW_DROP_DOWN,
    CREATE,
    DONE,
    DATE_RANGE,
    BUILD,
    FACE
}

fun getDefaultCounterData() =
    mutableStateListOf(
        CounterData(id = "first", iconType = CounterIconType.ACCOUNT_BOX, toggleValue = true, value = 1, isSelected = true),
        CounterData(id = "second", iconType = CounterIconType.ADD, value = 1),
        CounterData(id = "third", iconType = CounterIconType.ACCOUNT_CIRCLE, toggleValue = true, value = 1),
        CounterData(id = "forth", iconType = CounterIconType.CALL, value = 1),
        CounterData(id = "fifth", iconType = CounterIconType.ADD_CIRCLE, toggleValue = true, value = 1),
        CounterData(id = "sixth", iconType = CounterIconType.ARROW_DROP_DOWN, value = 1),
        CounterData(id = "seventh", iconType = CounterIconType.CREATE, toggleValue = true, value = 1),
        CounterData(id = "eighth", iconType = CounterIconType.DONE, value = 1),
        CounterData(id = "nineth", iconType = CounterIconType.DATE_RANGE, toggleValue = true, value = 1),
        CounterData(id = "tenth", iconType = CounterIconType.BUILD, value = 1),
        CounterData(id = "eleventh", iconType = CounterIconType.FACE, toggleValue = true, value = 1),
    )
