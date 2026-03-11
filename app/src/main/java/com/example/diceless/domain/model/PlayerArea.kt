package com.example.diceless.domain.model

import com.example.diceless.domain.model.enums.AlignmentEnum
import com.example.diceless.domain.model.enums.ProportionEnum
import com.example.diceless.domain.model.enums.RotationEnum

data class PlayerArea(
    val rotation: RotationEnum = RotationEnum.NONE,
    val proportion: ProportionEnum = ProportionEnum.FULL_SIZE,
    val alignment: AlignmentEnum = AlignmentEnum.CENTER
)