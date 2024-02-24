package com.example.x_chat.presentation.chat_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.presentation.util.CircleImg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    name: String,
    onBackClick: () -> Unit,
    onAccountClick: () -> Unit,
    image: String?
) {
    TopAppBar(
        modifier = Modifier
            .shadow(
                elevation = 5.dp,
                spotColor = Color.DarkGray,
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
            ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ),
        title = {
            Text(
                name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        actions = {
            Box {
                Row {
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = { onAccountClick() }) {
                        CircleImg(
                            modifier = Modifier.size(50.dp),
                            image = image
                        )
                    }
                }
            }
        }
    )
}