package com.example.x_chat.presentation.g_chat_info_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.x_chat.domain.model.api.rec.sub.OwnerModel

@Composable
fun GroupMembers(
    members: List<OwnerModel>,
    userId: String,
    admin: String,
    onRemoveClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isAdmin = userId == admin
    Column(modifier = modifier) {
        members.forEach { member ->
            val name = if (userId == member._id) "You" else member.username
            UserCard(
                name = name,
                isAdmin = isAdmin,
                iamAdmin = admin == member._id,
                id = member._id,
                onRemoveClick = {
                onRemoveClick(it)
                })

        }
    }
}