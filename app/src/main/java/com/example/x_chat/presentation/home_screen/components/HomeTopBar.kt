package com.example.x_chat.presentation.home_screen.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.x_chat.presentation.util.SearchBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    onSearchClick: () -> Unit,
    onAccountClick: () -> Unit,
    onNotiClick: () -> Unit
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
        title = { Text(text = "X Chat", fontSize = 30.sp) },
        actions = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(80.dp)
                    .clickable { onSearchClick() }
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 10.dp)
                )


            }
            Spacer(modifier = Modifier.width(10.dp))
            Card(
                shape = CircleShape, modifier = Modifier.size(40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ),
            ) {
                IconButton(onClick = { onAccountClick() }) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(25.dp)
                    )

                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Card(
                shape = CircleShape, modifier = Modifier.size(40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ),
            ) {
                IconButton(onClick = { onNotiClick() }) {
                    Icon(
                        imageVector = Icons.Default.NotificationImportant,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(25.dp)
                    )

                }
            }
            Spacer(modifier = Modifier.width(10.dp))
        })
}