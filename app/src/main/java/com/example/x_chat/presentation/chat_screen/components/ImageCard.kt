package com.example.x_chat.presentation.chat_screen.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.SubcomposeAsyncImage
import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.presentation.image_screen.ImageScreen
import com.example.x_chat.presentation.util.ImageLoadingUtility

@Composable
fun ImageCard(
    userId: String,
    messageModel: MessageModel,
) {
    val navigator = LocalNavigator.currentOrThrow
    val name = if (userId == messageModel.sender._id) "You" else messageModel.sender.username
    Box(
        modifier = Modifier.padding(5.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(5.dp),
                text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(180.dp)
                    .width(180.dp)
                    .padding(10.dp)
                    .clickable {
                        navigator.push(
                            ImageScreen(
                                url = messageModel.content,
                                name = name
                            )
                        )
                    },
                model = messageModel.content,
                contentScale = ContentScale.FillHeight,
                loading = {
                    ImageLoadingUtility()
                },
                contentDescription = null,
            )
        }
    }
}