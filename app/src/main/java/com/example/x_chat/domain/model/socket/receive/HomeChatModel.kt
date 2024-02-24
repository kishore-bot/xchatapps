package com.example.x_chat.domain.model.socket.receive

import com.example.x_chat.domain.model.socket.MessageModel
import kotlinx.serialization.Serializable

@Serializable
data class HomeChatModel(
    val chatId: String,
    val avatar: String? = "",
    val isGroup: Boolean,
    val lastMessage: MessageModel?,
    val name: String,
    val userId: String
)