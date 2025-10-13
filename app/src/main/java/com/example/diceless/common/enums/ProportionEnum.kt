package com.example.diceless.common.enums

enum class ProportionEnum(val width: Float, val height: Float) {
    FULL_SIZE(width = 1f, height = 1f),
    FULL_WIDTH_HALF_HEIGHT(width = 1f, height = 0.5f),
    HALF_WIDTH_FULL_HEIGHT(width = 0.5f, height = 1f),
    HALF_WIDTH_HALF_HEIGHT(width = 0.5f, height = 0.5f);
}