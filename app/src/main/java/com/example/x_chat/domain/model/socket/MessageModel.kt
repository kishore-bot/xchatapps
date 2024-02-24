package com.example.x_chat.domain.model.socket

import com.example.x_chat.domain.model.socket.receive.ReplayModel
import com.example.x_chat.domain.model.socket.receive.SenderModel
import kotlinx.serialization.Serializable

@Serializable
data class MessageModel(
    val __v: Int,
    val _id: String,
    val chatId: String,
    val content: String,
    val createdAt: String,
    val saved: Boolean,
    val sender: SenderModel,
    val status: String,
    val replay: ReplayModel? = null,
    val isFile: Boolean,
)