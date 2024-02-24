package com.example.x_chat.domain.model.socket.receive

import kotlinx.serialization.Serializable

@Serializable
data class SenderModel(
    val _id: String,
    val username: String
)