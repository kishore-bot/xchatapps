package com.example.x_chat.domain.usecase.socket

import com.example.x_chat.domain.repository.SocketRepository

class SocketConnection(
    private val socketRepository: SocketRepository
) {
    suspend operator fun invoke() {
        socketRepository.connection()
    }
}