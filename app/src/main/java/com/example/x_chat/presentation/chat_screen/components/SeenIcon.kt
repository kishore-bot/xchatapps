package com.example.x_chat.presentation.chat_screen.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.domain.model.socket.MessageModel

@Composable
fun SeenIcon(
    userId:String,
    isGroup:Boolean,
    message: MessageModel,
) {
    val isSeen = !isGroup && message.sender._id == userId

    val iconColor =
        if (message.status == "sent") Color.Blue else if (message.status == "received") Color.Red else Color.Green
    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 15.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = message.createdAt.substring(11, 16),
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.width(10.dp))
        if (isSeen) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Spacer(modifier = Modifier.width(17.dp))
        }
    }
}