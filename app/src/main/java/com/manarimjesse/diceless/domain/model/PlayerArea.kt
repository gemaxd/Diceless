package com.manarimjesse.diceless.domain.model

import com.manarimjesse.diceless.domain.model.enums.AlignmentEnum
import com.manarimjesse.diceless.domain.model.enums.ProportionEnum
import com.manarimjesse.diceless.domain.model.enums.RotationEnum

data class PlayerArea(
    val rotation: RotationEnum = RotationEnum.NONE,
    val proportion: ProportionEnum = ProportionEnum.FULL_SIZE,
    val alignment: AlignmentEnum = AlignmentEnum.CENTER
)