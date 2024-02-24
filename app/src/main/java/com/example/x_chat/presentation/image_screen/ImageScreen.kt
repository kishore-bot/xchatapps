package com.example.x_chat.presentation.image_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.SubcomposeAsyncImage
import com.example.x_chat.presentation.util.ImageLoadingUtility


data class ImageScreen(
    val name: String,
    val url: String? = "",
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ImageScreenImp(
            name = name,
            url = url,
            onBackClick = { navigator.pop() }
        )
    }
}

@Composable
fun ImageScreenImp(
    name: String,
    url: String? = "",
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = { onBackClick() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(30.dp))
            Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(100.dp))
        if (url == "") {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Black),
            )
        } else {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .padding(5.dp),
                model = url,
                contentScale = ContentScale.Fit,
                loading = {
                    ImageLoadingUtility()
                },
                contentDescription = null,
            )
        }
    }

}