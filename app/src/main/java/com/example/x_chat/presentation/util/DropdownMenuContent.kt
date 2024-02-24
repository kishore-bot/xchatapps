package com.example.x_chat.presentation.util

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset

@Composable
fun DropdownMenuContent(
    isContextMenuVisible: Boolean,
    onDismissRequest: () -> Unit,
    pressOffset: DpOffset,
    itemHeight: Dp,
    onItemClick: (DropDownItem) -> Unit,
    dropdownItems: List<DropDownItem>,
) {
    DropdownMenu(
        expanded = isContextMenuVisible,
        onDismissRequest = onDismissRequest,
        offset = pressOffset.copy(y = pressOffset.y - itemHeight)
    ) {
        dropdownItems.forEach {
            DropdownMenuItem(
                leadingIcon = { Icon(imageVector = it.icon, contentDescription = it.text) },
                text = { Text(text = it.text) },
                onClick = { onItemClick(it) }
            )
        }
    }
}