package com.example.x_chat.presentation.account_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.x_chat.R
import com.example.x_chat.presentation.util.CircleImg

@Composable
fun AccountImage(
    onImageClick: () -> Unit,
    isIcon: Boolean = true,
    imageUrl: String? = ""
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

            .height(250.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(150.dp)
                .fillMaxWidth()
                .aspectRatio(3f / 2f),
            contentScale = ContentScale.Fit
        )
        if (isIcon) {
            IconButton(onClick = {
                onImageClick();
            }, modifier = Modifier.offset(x = (330).dp)) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center)
                )
            }
        }
        CircleImg(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.Center)
                .offset(y = (30).dp),
            image = imageUrl
        )
    }
}