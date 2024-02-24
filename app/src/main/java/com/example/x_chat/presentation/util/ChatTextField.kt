package com.example.x_chat.presentation.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.R

@Composable
fun ChatTextField(
    textState: MutableState<String>,
    text: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,

            ),
        value = textState.value,
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        onValueChange = { newText -> textState.value = newText },
        label = { Text(text, color = colorResource(id = R.color.Text)) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        shape = RoundedCornerShape(15.dp),
    )
}