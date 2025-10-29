package com.example.diceless.domain.model.extension

import com.example.diceless.common.enums.SchemeEnum

fun calculateRotation(
    schemeEnum: SchemeEnum,
    offsetX: Float,
    offsetY: Float,
    isPristine: Boolean
): Float {
    if (isPristine) return 0f

    return when (schemeEnum) {
        SchemeEnum.SOLO -> 0f
        SchemeEnum.VERSUS_OPPOSITE -> {
            if (offsetY <= -5f) 180f else 0f
        }
        SchemeEnum.TRIPLE_STANDARD -> {
            if (offsetY <= -5f) {
                if (offsetX <= -5f) 90f else 270f
            } else {
                0f
            }
        }
        SchemeEnum.QUADRA_STANDARD -> {
            if (offsetY <= -5f) {
                if (offsetX <= -5f) 90f else 270f
            } else {
                if (offsetX <= -5f) 90f else 270f
            }
        }
    }
}

