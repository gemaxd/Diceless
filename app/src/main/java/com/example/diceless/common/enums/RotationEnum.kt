package com.example.diceless.common.enums

enum class RotationEnum(val degrees: Float) {
    NONE(degrees = 0f),
    INVERTED(degrees = 180f),
    RIGHT(degrees = 90f),
    LEFT(degrees = 270f);
}