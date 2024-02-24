package com.example.x_chat.presentation.image_upload_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageUploadBottomBar(
    onCamFlipClick: () -> Unit,
    onCamClick: () -> Unit,
    onSelectImageClick: () -> Unit,

    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onCamFlipClick() }) {
            Icon(
                imageVector = Icons.Default.FlipCameraAndroid,
                contentDescription = null,
                Modifier.size(50.dp)
            )
        }
        IconButton(modifier = Modifier.size(100.dp), onClick = { onCamClick() }) {
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = Icons.Outlined.Circle,
                contentDescription = null,
            )
        }
        IconButton(onClick = { onSelectImageClick() }) {
            Icon(imageVector = Icons.Default.Image, contentDescription = null, Modifier.size(50.dp))
        }
    }
}