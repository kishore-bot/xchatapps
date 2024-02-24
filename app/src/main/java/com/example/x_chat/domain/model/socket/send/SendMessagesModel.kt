package com.example.x_chat.domain.model.socket.send

import kotlinx.serialization.Serializable

@Serializable
data class SendMessagesModel(
    val chatId: String,
    val message: String,
    val replayId:String? = null,
    val isFile:Boolean = false
)