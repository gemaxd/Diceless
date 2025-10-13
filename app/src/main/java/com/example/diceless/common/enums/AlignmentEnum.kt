package com.example.diceless.common.enums

import androidx.compose.ui.Alignment

enum class AlignmentEnum(val align: Alignment) {
    CENTER(align = Alignment.Center),
    TOP_START(align = Alignment.TopStart),
    TOP_CENTER(align = Alignment.TopCenter),
    TOP_END(align = Alignment.TopEnd),
    BOTTOM_START(align = Alignment.BottomStart),
    BOTTOM_CENTER(align = Alignment.BottomCenter),
    BOTTOM_END(align = Alignment.BottomEnd)
}