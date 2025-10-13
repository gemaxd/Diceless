package com.example.diceless.common.utils

import androidx.compose.ui.Alignment
import com.example.diceless.common.enums.AlignmentEnum
import com.example.diceless.common.enums.PositionEnum
import com.example.diceless.common.enums.ProportionEnum
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.common.enums.SchemeEnum
import com.example.diceless.domain.model.PlayerArea

fun getCorrectOrientation(position: PositionEnum, scheme: SchemeEnum): PlayerArea? {
    return when(scheme){
        SchemeEnum.SOLO -> soloSchemePlayerArea(position)
        SchemeEnum.VERSUS_OPPOSITE -> versusSchemePlayerArea(position)
        SchemeEnum.VERSUS_SIDE_BY_SIDE -> versusSideBySideSchemePlayerArea(position)
        SchemeEnum.TRIPLE_STANDARD -> tripleStandardSchemePlayerArea(position)
        SchemeEnum.QUADRA_STANDARD -> quadraStandardSchemePlayerArea(position)
        SchemeEnum.QUADRA_VERTICAL -> quadraVerticalSchemePlayerArea(position)
    }
}

fun soloSchemePlayerArea(position: PositionEnum): PlayerArea? {
    return when(position){
        PositionEnum.PLAYER_ONE -> {
            PlayerArea(
                rotation = RotationEnum.NONE,
                proportion = ProportionEnum.FULL_SIZE,
                alignment = AlignmentEnum.CENTER
            )
        }
        else -> null
    }
}

fun versusSchemePlayerArea(position: PositionEnum): PlayerArea? {
    return when(position){
        PositionEnum.PLAYER_ONE -> {
            PlayerArea(
                rotation = RotationEnum.NONE,
                proportion = ProportionEnum.FULL_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.BOTTOM_CENTER
            )
        }
        PositionEnum.PLAYER_TWO -> {
            PlayerArea(
                rotation = RotationEnum.INVERTED,
                proportion = ProportionEnum.FULL_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_CENTER

            )
        }
        else -> null
    }
}

fun versusSideBySideSchemePlayerArea(position: PositionEnum): PlayerArea? {
    return when(position){
        PositionEnum.PLAYER_ONE -> {
            PlayerArea(
                rotation = RotationEnum.RIGHT,
                proportion = ProportionEnum.FULL_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.BOTTOM_CENTER
            )
        }
        PositionEnum.PLAYER_TWO -> {
            PlayerArea(
                rotation = RotationEnum.RIGHT,
                proportion = ProportionEnum.FULL_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_CENTER
            )
        }
        else -> null
    }
}

fun tripleStandardSchemePlayerArea(position: PositionEnum): PlayerArea? {
    return when(position){
        PositionEnum.PLAYER_ONE -> {
            PlayerArea(
                rotation = RotationEnum.NONE,
                proportion = ProportionEnum.FULL_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.BOTTOM_CENTER
            )
        }
        PositionEnum.PLAYER_TWO -> {
            PlayerArea(
                rotation = RotationEnum.RIGHT,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_START
            )
        }
        PositionEnum.PLAYER_THREE -> {
            PlayerArea(
                rotation = RotationEnum.LEFT,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_END
            )
        }
        else -> null
    }
}

fun quadraStandardSchemePlayerArea(position: PositionEnum): PlayerArea {
    return when(position){
        PositionEnum.PLAYER_ONE -> {
            PlayerArea(
                rotation = RotationEnum.RIGHT,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.BOTTOM_START
            )
        }
        PositionEnum.PLAYER_TWO -> {
            PlayerArea(
                rotation = RotationEnum.RIGHT,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_START
            )
        }
        PositionEnum.PLAYER_THREE -> {
            PlayerArea(
                rotation = RotationEnum.LEFT,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_END
            )
        }
        PositionEnum.PLAYER_FOUR -> {
            PlayerArea(
                rotation = RotationEnum.LEFT,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.BOTTOM_END
            )
        }
    }
}

fun quadraVerticalSchemePlayerArea(position: PositionEnum): PlayerArea {
    return when(position){
        PositionEnum.PLAYER_ONE -> {
            PlayerArea(
                rotation = RotationEnum.NONE,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.BOTTOM_START
            )
        }
        PositionEnum.PLAYER_TWO -> {
            PlayerArea(
                rotation = RotationEnum.INVERTED,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_START
            )
        }
        PositionEnum.PLAYER_THREE -> {
            PlayerArea(
                rotation = RotationEnum.INVERTED,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.TOP_END
            )
        }
        PositionEnum.PLAYER_FOUR -> {
            PlayerArea(
                rotation = RotationEnum.NONE,
                proportion = ProportionEnum.HALF_WIDTH_HALF_HEIGHT,
                alignment = AlignmentEnum.BOTTOM_END
            )
        }
    }
}