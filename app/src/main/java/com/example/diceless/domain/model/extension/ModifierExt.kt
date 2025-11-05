package com.example.diceless.domain.model.extension

import android.util.Size
import androidx.compose.animation.animateContentSize
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.ui.battlegrid.components.draggable.RotationZone
import com.example.diceless.ui.battlegrid.components.draggable.toDegrees

fun calculateRotation(
    schemeEnum: SchemeEnum,
    offsetX: Float,
    offsetY: Float,
    isPristine: Boolean,
    maxHeightPx: Float
): Float {
    if (isPristine) return RotationZone.PRISTINE.toDegrees()
    if (schemeEnum == SchemeEnum.SOLO) return RotationZone.PRISTINE.toDegrees()

    // Define thresholds como % da tela
    val topZoneThreshold = maxHeightPx * -1 // 35% da altura = zona superior
    val leftZoneThreshold = -5f  // 35% da largura = zona esquerda

    return when (schemeEnum) {
        SchemeEnum.VERSUS_OPPOSITE -> {
            if (offsetY <= -topZoneThreshold) {
                RotationZone.OPPOSITE.toDegrees()
            } else {
                RotationZone.PRISTINE.toDegrees()
            }
        }

        SchemeEnum.TRIPLE_STANDARD -> {
            if (offsetY <= -topZoneThreshold) {
                if (offsetX <= -leftZoneThreshold) {
                    RotationZone.LEFT.toDegrees()
                } else {
                    RotationZone.RIGHT.toDegrees()
                }
            } else {
                RotationZone.PRISTINE.toDegrees()
            }
        }

        SchemeEnum.QUADRA_STANDARD -> {
            if (offsetY <= -topZoneThreshold) {
                if (offsetX <= -leftZoneThreshold) {
                    RotationZone.LEFT.toDegrees()
                } else {
                    RotationZone.RIGHT.toDegrees()
                }
            } else {
                if (offsetX <= -leftZoneThreshold) {
                    RotationZone.LEFT.toDegrees()
                } else {
                    RotationZone.RIGHT.toDegrees()
                }
            }
        }

        else -> RotationZone.PRISTINE.toDegrees()
    }
}
