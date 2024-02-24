package com.example.x_chat.domain.model.socket.send

import kotlinx.serialization.Serializable

@Serializable
data class MessageOperationsModel(
    val chatId: String,
    val messageId: String,
    val userId: String
)