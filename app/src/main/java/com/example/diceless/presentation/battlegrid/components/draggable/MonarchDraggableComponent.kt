package com.example.diceless.presentation.battlegrid.components.draggable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.extension.calculateRotation
import kotlin.math.roundToInt

@Composable
fun MonarchDraggableComponent(schemeEnum: SchemeEnum, maxHeight: Dp) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val targetRotation = remember(schemeEnum, offsetX, offsetY) {
        calculateRotation(
            schemeEnum = schemeEnum,
            offsetX = offsetX,
            offsetY = offsetY,
            isPristine = offsetY == 0f && offsetX == 0f,
            maxHeightPx = maxHeight.value
        )
    }

    val rotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(100.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
            .rotate(rotation),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color.Red
        )
    }
}