package com.example.x_chat.domain.usecase.chat

import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ReceiveSeenMessage (
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): Flow<MessageModel> {
        return chatRepository.receiveSeenMessage()
    }
}