package com.example.x_chat.presentation.in_app_notification_screen.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.x_chat.presentation.util.ChatButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotiTopBar(
    onBackClick: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.shadow(
            elevation = 5.dp,
            spotColor = Color.DarkGray,
            shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
        ),
        title = {
            Text(text = "Notifications")
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
    )
}