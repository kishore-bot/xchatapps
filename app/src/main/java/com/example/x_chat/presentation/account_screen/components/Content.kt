package com.example.x_chat.presentation.account_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Content(
    label: String,
    value: String
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Color.LightGray),
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.W600)
            Text(
                text = value,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                fontWeight = FontWeight.W600
            )
        }
    }
}
//user.createAt.substring(0, 10)