package com.example.x_chat.domain.usecase.chat

import com.example.x_chat.domain.model.socket.send.MessageOperationsModel
import com.example.x_chat.domain.repository.ChatRepository

class SendSeenMessage(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(message: MessageOperationsModel) {
        chatRepository.sendSeenMessage(message)
    }
}