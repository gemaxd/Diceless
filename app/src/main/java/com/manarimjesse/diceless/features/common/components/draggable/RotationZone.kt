package com.manarimjesse.diceless.presentation.battlegrid.components.draggable

enum class RotationZone {
    PRISTINE,      // 0°
    OPPOSITE,      // 180°
    LEFT,          // 90°
    RIGHT          // 270°
}

fun RotationZone.toDegrees(): Float = when (this) {
    RotationZone.PRISTINE -> 0f
    RotationZone.OPPOSITE -> 180f
    RotationZone.LEFT -> 90f
    RotationZone.RIGHT -> 270f
}