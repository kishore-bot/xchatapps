package com.example.x_chat.domain.usecase

import com.example.x_chat.domain.usecase.socket.GetHomeChat
import com.example.x_chat.domain.usecase.socket.SocketConnection

data class SocketUseCase(
    val connect: SocketConnection,
    val getHomeChat: GetHomeChat,
)