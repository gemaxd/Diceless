package com.example.diceless.common.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import kotlin.div
import kotlin.unaryMinus

fun Modifier.vertical() = layout { measurable, constraints ->
    val rotatedConstraints = Constraints(
        minWidth = constraints.minHeight,
        maxWidth = constraints.maxHeight,
        minHeight = constraints.minWidth,
        maxHeight = constraints.maxWidth
    )
    val placeable = measurable.measure(rotatedConstraints)
    layout(placeable.height, placeable.width) {
        placeable.place(
            x = -(placeable.width / 2 - placeable.height / 2),
            y = -(placeable.height / 2 - placeable.width / 2)
        )
    }
}