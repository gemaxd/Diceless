package com.example.diceless.domain.model.extension

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.example.diceless.common.enums.SchemeEnum

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun getDraggableRotation(schemeEnum: SchemeEnum, offsetX: Float, offsetY: Float) =
    when(schemeEnum){
        SchemeEnum.SOLO -> Modifier
        SchemeEnum.VERSUS_OPPOSITE -> {
            Modifier.then(
                other = if (offsetY <= -5.0){
                    Modifier.rotate(180f)
                } else {
                    Modifier.rotate(0f)
                }
            )
        }
        SchemeEnum.TRIPLE_STANDARD -> {
            Modifier.then(
                other = if (offsetY <= -5.0){
                    if (offsetX <= -5.0){
                        Modifier.rotate(90f)
                    } else {
                        Modifier.rotate(270f)
                    }
                } else {
                    Modifier.rotate(0f)
                }
            )
        }
        SchemeEnum.QUADRA_STANDARD -> {
            Modifier.then(
                other = if (offsetY <= -5.0){
                    if (offsetX <= -5.0){
                        Modifier.rotate(90f)
                    } else {
                        Modifier.rotate(270f)
                    }
                } else {
                    if (offsetX <= -5.0){
                        Modifier.rotate(90f)
                    } else {
                        Modifier.rotate(270f)
                    }
                }
            )
        }
    }
