package com.example.x_chat.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import com.example.x_chat.R

@Composable
fun CircleImg(
    modifier: Modifier = Modifier,
    image: String? = "",
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
    ) {
        if (image == ""|| image ==null) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        } else {
            SubcomposeAsyncImage(
                model = image,
                contentScale = ContentScale.FillBounds,
                loading = {
                    ImageLoadingUtility()
                },
                contentDescription = null,
            )
        }
    }
}