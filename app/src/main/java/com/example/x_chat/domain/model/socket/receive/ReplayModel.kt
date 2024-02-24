package com.example.x_chat.domain.model.socket.receive

import kotlinx.serialization.Serializable

@Serializable
data class ReplayModel(
    val _id: String,
    val content: String
)