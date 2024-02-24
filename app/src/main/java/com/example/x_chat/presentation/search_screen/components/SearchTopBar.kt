package com.example.x_chat.presentation.search_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier.shadow(
            elevation = 5.dp,
            spotColor = Color.DarkGray,
            shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
        ),
        title = {
            SearchBar(modifier = Modifier
                .width(300.dp),
                query = text,
                onQueryChange = {
                    text = it
                },
                onSearch = {
                    active = false
                    onSearch(it)
                    text = ""
                },
                active = active,
                onActiveChange = {
                },
                placeholder = {
                    Text(text = "Search By Name")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }) {}
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

