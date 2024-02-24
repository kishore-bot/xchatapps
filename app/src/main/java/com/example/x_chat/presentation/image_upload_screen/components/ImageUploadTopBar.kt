package com.example.x_chat.presentation.image_upload_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.presentation.util.ChatButton


@Composable
fun ImageUploadTopBar(
    onBackClick: () -> Unit,
    onUploadClick: () -> Unit,
    isBack: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackClick() }) {
            if(isBack){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    Modifier.size(50.dp)
                )
            }else{
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    Modifier.size(50.dp)
                )
            }
        }
        Text(text = "Upload An Image", fontSize = 20.sp)
        Spacer(modifier = Modifier.width(20.dp))
        ChatButton(
            title = "Upload", modifier = Modifier
                .width(150.dp)
                .height(45.dp)
        ) {
            onUploadClick()
        }
        Spacer(modifier = Modifier.width(10.dp))
    }
}