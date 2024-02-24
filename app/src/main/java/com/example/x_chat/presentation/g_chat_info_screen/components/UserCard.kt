package com.example.x_chat.presentation.g_chat_info_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun UserCard(
    name: String,
    isAdmin: Boolean,
    iamAdmin: Boolean,
    onRemoveClick: (String) -> Unit,
    id: String,
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Color.LightGray),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name : ",
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(10.dp))
            if (isAdmin && !iamAdmin) {
                IconButton(onClick = { onRemoveClick(id) }) {
                    Icon(
                        imageVector = Icons.Default.RemoveCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            if (iamAdmin) {
                Text(
                    text = "Amin",
                    fontSize = 15.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}