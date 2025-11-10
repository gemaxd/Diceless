package com.example.diceless.presentation.battlegrid.components.life

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.byValue
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly


@Composable
fun CustomLifeInputter(
    currentLife: String = "40",
    onConfirmLife: (String) -> Unit
){
    var customLife = rememberTextFieldState("")

    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Insira um valor para vida",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                state = customLife,
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done
                ),
                inputTransformation =
                    InputTransformation.byValue { value, new ->
                        val returnString = if(new.isNotEmpty() && new.isDigitsOnly()) new else value
                        returnString
                    },
                placeholder = {
                    Text(
                        text = currentLife,
                        fontSize = 24.sp
                    )
                }
            )

            Row(
                modifier = Modifier.padding(8.dp),
                Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        customLife.clearText()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }

                IconButton(
                    onClick = {
                        onConfirmLife(customLife.text.toString())
                    }
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomLifeInputter(){
    CustomLifeInputter(
        onConfirmLife = {}
    )
}
