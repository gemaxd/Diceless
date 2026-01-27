package com.example.diceless.common.extensions

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.PositionEnum.*
import com.example.diceless.common.enums.RotationEnum
import kotlin.div
import kotlin.unaryMinus

private val MEDIUM_PADDING = 8.dp
private val SMALL_PADDING = 4.dp

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

fun Modifier.paddingBasedOnPosition(positionEnum: PositionEnum, rotationEnum: RotationEnum) : Modifier {
    return when (positionEnum) {
        PLAYER_ONE -> {
            if (rotationEnum.isVerticalRotated()) {
                Modifier.padding(start = MEDIUM_PADDING, bottom = MEDIUM_PADDING, end = SMALL_PADDING, top = SMALL_PADDING)
            } else {
                Modifier.padding(start = MEDIUM_PADDING, bottom = MEDIUM_PADDING, end = MEDIUM_PADDING, top = SMALL_PADDING)
            }
        }
        PLAYER_TWO -> {
            if (rotationEnum.isVerticalRotated()){
                if (rotationEnum == RotationEnum.LEFT){
                    Modifier.padding(start = SMALL_PADDING, bottom = SMALL_PADDING, end = MEDIUM_PADDING, top = MEDIUM_PADDING)
                } else {
                    Modifier.padding(start = MEDIUM_PADDING, bottom = SMALL_PADDING, end = SMALL_PADDING, top = MEDIUM_PADDING)
                }
            } else {
                Modifier.padding(start = MEDIUM_PADDING, bottom = SMALL_PADDING, end = MEDIUM_PADDING, top = MEDIUM_PADDING)
            }
        }
        PLAYER_THREE -> {
            if (rotationEnum == RotationEnum.LEFT){
                Modifier.padding(start = SMALL_PADDING, bottom = SMALL_PADDING, end = MEDIUM_PADDING, top = MEDIUM_PADDING)
            } else {
                Modifier.padding(start = MEDIUM_PADDING, bottom = SMALL_PADDING, end = SMALL_PADDING, top = MEDIUM_PADDING)
            }
        }
        PLAYER_FOUR -> {
            if (rotationEnum == RotationEnum.LEFT){
                Modifier.padding(start = SMALL_PADDING, bottom = MEDIUM_PADDING, end = MEDIUM_PADDING, top = SMALL_PADDING)
            } else {
                Modifier.padding(start = MEDIUM_PADDING, bottom = MEDIUM_PADDING, end = SMALL_PADDING, top = SMALL_PADDING)
            }
        }
    }
}