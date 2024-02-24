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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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

@Composable
fun BottomBar(
    textState: MutableState<String>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onNavigateToImageUpload: () -> Unit
) {
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = modifier
                .background(Color.Transparent)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                modifier = Modifier.width(320.dp),
                value = textState.value,
                onValueChange = { newText -> textState.value = newText },
                maxLines = 10,
                placeholder = { Text(text = "Conversation....!") },
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
                ),
                trailingIcon = {
                    IconButton(onClick = { onNavigateToImageUpload() }) {
                        Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null)
                    }
                }

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
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(40.dp)
                    )

                }
            }
        }
    }
}