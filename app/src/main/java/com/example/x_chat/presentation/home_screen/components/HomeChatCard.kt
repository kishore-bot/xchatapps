package com.example.x_chat.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.R
import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.presentation.util.CircleImg

@Composable
fun HomeChatCard(
    isMessageToRead: Boolean,
    name: String,
    onClick: () -> Unit,
    image: String?,
    message: MessageModel? = null
) {

    val weight = if (isMessageToRead) {
        FontWeight.Medium
    } else {
        FontWeight.W200
    }
    val color = if (isMessageToRead) {
        Color.Black
    } else {
        Color.LightGray
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(100.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                colorResource(
                    id = (R.color.SendCard)
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ) {
            CircleImg(modifier = Modifier.size(60.dp),image)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                if (message != null) {
                    if (!message.isFile) {
                        Text(
                            text = message.content,
                            fontSize = 20.sp,
                            fontWeight = weight,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = color,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Image",
                                fontSize = 20.sp,
                                fontWeight = weight,
                                maxLines = 1,
                                overflow = TextOverflow.Clip
                            )
                        }
                    }
                }
            }
        }
    }

}
