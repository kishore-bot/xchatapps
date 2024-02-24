package com.example.x_chat.presentation.chat_screen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.domain.model.socket.MessageModel

@Composable
fun ChatCardContent(
    userId: String,
    message: MessageModel,
    onReplayClick: (String) -> Unit,
) {
    val name = if (userId == message.sender._id) "You" else message.sender.username

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (message.replay != null) {
            Box(modifier = Modifier.clickable { onReplayClick(message.replay._id) }) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Reply, contentDescription = null)
                    Text(
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        text = message.replay.content,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .padding(start = 5.dp)
                .padding(5.dp)

        ) {
            Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .background(Color.Blue)
                    .width(5.dp)
                    .height(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = message.content,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Clip
            )
            Spacer(modifier = Modifier.width(10.dp).height(5.dp))
        }
    }
}
