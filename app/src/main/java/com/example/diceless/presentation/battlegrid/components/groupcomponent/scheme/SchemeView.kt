package com.example.diceless.presentation.battlegrid.components.groupcomponent.scheme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceless.common.enums.SchemeEnum

@Composable
fun SchemeView(
    selectedScheme: SchemeEnum,
    schemeEnum: SchemeEnum,
    onSelectScheme: (SchemeEnum) -> Unit
) {
    when (schemeEnum) {
        SchemeEnum.SOLO -> {
            SchemeViewSolo(
                isSelected = selectedScheme == schemeEnum,
                onSelectScheme = onSelectScheme
            )
        }

        SchemeEnum.VERSUS_OPPOSITE -> {
            SchemeViewVersusOpposite(
                isSelected = selectedScheme == schemeEnum,
                onSelectScheme = onSelectScheme
            )
        }

        SchemeEnum.TRIPLE_STANDARD -> {
            SchemeViewTripleStandard(
                isSelected = selectedScheme == schemeEnum,
                onSelectScheme = onSelectScheme
            )
        }

        SchemeEnum.QUADRA_STANDARD -> {
            SchemeViewQuadraStandard(
                isSelected = selectedScheme == schemeEnum,
                onSelectScheme = onSelectScheme
            )
        }
    }
}

@Composable
fun SchemeViewSolo(
    isSelected: Boolean,
    onSelectScheme: (SchemeEnum) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    onSelectScheme(SchemeEnum.SOLO)
                }
            )
            .height(400.dp)
            .width(200.dp)
            .padding(8.dp)
            .border(
                border = BorderStroke(2.dp,
                    if (isSelected) Color.Red else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Card(
            modifier = Modifier
                .height(400.dp)
                .width(200.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) Color.Red else Color.LightGray
            )
        ) {}
    }
}

@Composable
fun SchemeViewVersusOpposite(
    isSelected: Boolean,
    onSelectScheme: (SchemeEnum) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    onSelectScheme(SchemeEnum.VERSUS_OPPOSITE)
                }
            )
            .height(400.dp)
            .width(200.dp)
            .padding(8.dp)
            .border(
                border = BorderStroke(2.dp, if (isSelected) Color.Red else Color.LightGray),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Card(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) Color.Red else Color.LightGray
            )
        ) {}

        Card(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) Color.Red else Color.LightGray
            )
        ) { }
    }
}

@Composable
fun SchemeViewTripleStandard(
    isSelected: Boolean,
    onSelectScheme: (SchemeEnum) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    onSelectScheme(SchemeEnum.TRIPLE_STANDARD)
                }
            )
            .height(400.dp)
            .width(200.dp)
            .padding(8.dp)
            .border(
                border = BorderStroke(2.dp, if (isSelected) Color.Red else Color.LightGray),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Row {
            Card(
                modifier = Modifier
                    .height(196.dp)
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Red else Color.LightGray
                )
            ) { }

            Card(
                modifier = Modifier
                    .height(196.dp)
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Red else Color.LightGray
                )
            ) { }
        }

        Card(
            modifier = Modifier
                .height(196.dp)
                .width(200.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) Color.Red else Color.LightGray
            )
        ) { }
    }
}

@Composable
fun SchemeViewQuadraStandard(
    isSelected: Boolean,
    onSelectScheme: (SchemeEnum) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    onSelectScheme(SchemeEnum.QUADRA_STANDARD)
                }
            )
            .height(400.dp)
            .width(200.dp)
            .padding(8.dp)
            .border(
                border = BorderStroke(2.dp, if (isSelected) Color.Red else Color.LightGray),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Row {
            Card(
                modifier = Modifier
                    .height(192.dp)
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Red else Color.LightGray
                )
            ) { }

            Card(
                modifier = Modifier
                    .height(192.dp)
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Red else Color.LightGray
                )
            ) { }
        }

        Row {
            Card(
                modifier = Modifier
                    .height(192.dp)
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Red else Color.LightGray
                )
            ) { }

            Card(
                modifier = Modifier
                    .height(192.dp)
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Red else Color.LightGray
                )
            ) { }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSchemeViewSolo() {
    SchemeViewSolo(isSelected = true, onSelectScheme = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewSchemeViewVersusOpposite() {
    SchemeViewVersusOpposite(isSelected = false, onSelectScheme = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewSchemeViewTripleStandard() {
    SchemeViewTripleStandard(isSelected = false, onSelectScheme = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewSchemeViewQuadraStandard() {
    SchemeViewQuadraStandard(isSelected = false, onSelectScheme = {})
}