package com.example.x_chat.presentation.home_screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeFAB(
    onFabClick: () -> Unit,
) {
ExtendedFloatingActionButton(onClick = {onFabClick()}) {
    Icon(imageVector = Icons.Filled.Edit, contentDescription = "", modifier = Modifier.size(25.dp))
    Spacer(modifier = Modifier.width(5.dp))
    Text(text = "Chat", fontSize = 20.sp)
    
}

}