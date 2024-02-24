package com.example.x_chat.domain.usecase.socket

import com.example.x_chat.domain.model.socket.receive.HomeChatList
import com.example.x_chat.domain.repository.SocketRepository
import kotlinx.coroutines.flow.Flow

class GetHomeChat (
    private val socketRepository: SocketRepository
) {
    suspend operator fun invoke(): Flow<HomeChatList> {
        return socketRepository.getHomeChatList()
    }
}