package com.example.diceless.presentation.battlegrid.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.RotationEnum
import com.example.diceless.domain.model.CounterData
import com.example.diceless.domain.model.CounterIconType
import com.example.diceless.domain.model.toImageVector

@Composable
fun CounterControlButton(
    rotationEnum: RotationEnum = RotationEnum.NONE,
    counter: CounterData,
    onToggle: () -> Unit = {},
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {}
) {
    if(counter.isToggle().not()){
        CounterControlForm(
            rotationEnum = rotationEnum,
            counter = counter,
            onIncrement = onIncrement,
            onDecrement = onDecrement
        )
    } else {
        CounterToggleForm(
            rotationEnum = rotationEnum,
            counter = counter,
            onToggle = onToggle
        )
    }
}

@Composable
fun CounterControlForm(
    rotationEnum: RotationEnum = RotationEnum.NONE,
    counter: CounterData,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center
    ){
        val iconTint = counter.value?.let { value ->
            if (value > 0) Color.LightGray else Color.Black
        } ?: Color.Black

        Icon(
            modifier = Modifier.rotate(rotationEnum.degrees),
            imageVector = counter.iconType.toImageVector(),
            contentDescription = null,
            tint = iconTint
        )

        counter.value?.let { value ->
            if (value > 0){
                Text(
                    modifier = Modifier.rotate(rotationEnum.degrees),
                    text = value.toString(),
                    fontWeight = FontWeight.Black
                )
            }
        }

        when(rotationEnum){
            RotationEnum.NONE, RotationEnum.INVERTED -> {
                Column(
                    modifier = Modifier.rotate(rotationEnum.degrees)
                ) {
                    IconButton(
                        onClick = {
                            onIncrement.invoke()
                        }
                    ) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
                    }
                    IconButton(
                        onClick = {
                            onDecrement.invoke()
                        }
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }
                }
            }
            RotationEnum.LEFT -> {}
            RotationEnum.RIGHT -> {}
        }
    }
}

@Composable
fun CounterToggleForm(
    rotationEnum: RotationEnum = RotationEnum.NONE,
    counter: CounterData,
    onToggle: () -> Unit = {}
){
    val counterColors = counter.toggleValue?.let { value ->
        if (value)
            Pair(Color.Yellow, Color.Black)
        else
            Pair(Color.Black, Color.Transparent)
    } ?: Pair(Color.Black, Color.Transparent)

    when(rotationEnum){
        RotationEnum.NONE, RotationEnum.INVERTED -> {
            Box(
                modifier = Modifier
                    .background(color = counterColors.second)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ){
                IconButton(
                    modifier = Modifier.rotate(rotationEnum.degrees),
                    onClick = {
                        onToggle.invoke()
                    }
                ) {
                    Icon(
                        counter.iconType.toImageVector(),
                        contentDescription = null,
                        tint = counterColors.first
                    )
                }
            }
        }
        RotationEnum.LEFT -> {}
        RotationEnum.RIGHT -> {}
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewCounterControlButton(){
    CounterControlButton(
        counter = CounterData(
            id = "first",
            iconType = CounterIconType.FACE,
            value = 1
        ),
        onToggle = {},
        onIncrement = {},
        onDecrement = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCounterControlButtonInverted(){
    CounterControlButton(
        rotationEnum = RotationEnum.INVERTED,
        counter = CounterData(
            id = "first",
            iconType = CounterIconType.FACE,
            value = 1
        ),
        onToggle = {},
        onIncrement = {},
        onDecrement = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCounterToggleButton(){
    CounterControlButton(
        counter = CounterData(
            id = "first",
            iconType = CounterIconType.FACE,
            value = 1,
            toggleValue = false
        ),
        onToggle = {},
        onIncrement = {},
        onDecrement = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCounterToggleButtonInverted(){
    CounterControlButton(
        rotationEnum = RotationEnum.INVERTED,
        counter = CounterData(
            id = "first",
            iconType = CounterIconType.FACE,
            value = 1,
            toggleValue = false
        ),
        onToggle = {},
        onIncrement = {},
        onDecrement = {}
    )
}
