package com.example.x_chat.presentation.chat_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReplayBottomBar(
    textState: MutableState<String>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    messageModel: String,
    ) {
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = modifier
                .background(Color.Transparent)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.width(320.dp),
                value = textState.value,
                onValueChange = { newText -> textState.value = newText },
                maxLines = 10,
                placeholder = { Text(text = messageModel, maxLines = 1, fontSize = 20.sp) },
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                )

            )
            Spacer(modifier = Modifier.width(10.dp))
            Card(
                shape = CircleShape, modifier = Modifier.size(50.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ),
            ) {
                IconButton(onClick = { onClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Reply,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(30.dp)
                    )

                }
            }
        }
    }
}