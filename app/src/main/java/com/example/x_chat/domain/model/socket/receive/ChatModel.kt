package com.example.x_chat.domain.model.socket.receive

import com.example.x_chat.domain.model.socket.MessageModel
import kotlinx.serialization.Serializable

@Serializable
data class ChatModel(
    val chat: List<MessageModel>
)
