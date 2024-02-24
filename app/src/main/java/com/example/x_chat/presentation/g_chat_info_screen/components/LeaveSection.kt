package com.example.x_chat.presentation.g_chat_info_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LeaveSection(onLeaveClick: () -> Unit) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Leave From Group",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { onLeaveClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(30.dp)
            )

        }
    }
}