package com.example.diceless.ui.battlegrid.components.draggable

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.extension.getDraggableRotation
import kotlin.math.roundToInt

@Composable
fun DraggableElement2D(schemeEnum: SchemeEnum) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(100.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume() // Consome o gesto
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }.then(
                getDraggableRotation(schemeEnum, offsetX = offsetX, offsetY = offsetY)
//                other = if (offsetY <= 0){
//                    Modifier.rotate(180f)
//                } else {
//                    Modifier.rotate(0f)
//                }
            ),/*.getDraggableRotation(
                schemeEnum = schemeEnum,
                offsetX = offsetX,
                offsetY = offsetY
            ),*/
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            contentDescription = null,
            imageVector = Icons.Default.Favorite,
            tint = Color.Red
        )
    }
}