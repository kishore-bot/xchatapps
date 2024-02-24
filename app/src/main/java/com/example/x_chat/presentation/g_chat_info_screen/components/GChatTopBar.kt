package com.example.x_chat.presentation.g_chat_info_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GChatTopBar(
    onBackClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .padding(start = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = { onBackClick() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(30.dp))
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text("Details Screen", fontSize = 25.sp)
    }
}