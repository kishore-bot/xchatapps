package com.example.x_chat.presentation.util


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onSearchClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray)
            .clickable { onSearchClick() }

    ) {
        Icon(
            modifier = iconModifier,
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.Red,
        )
    }
}