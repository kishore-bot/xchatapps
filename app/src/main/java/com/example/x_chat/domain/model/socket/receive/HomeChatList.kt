package com.example.x_chat.domain.model.socket.receive

import kotlinx.serialization.Serializable

@Serializable
data class HomeChatList(
    val chat: List<HomeChatModel>?
)