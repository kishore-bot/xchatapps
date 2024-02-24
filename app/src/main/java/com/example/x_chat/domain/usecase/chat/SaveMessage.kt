package com.example.x_chat.domain.usecase.chat

import com.example.x_chat.domain.model.socket.send.MessageOperationsModel
import com.example.x_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SaveMessage(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(message: MessageOperationsModel): Flow<Boolean> {
        return chatRepository.saveMessage(message)
    }
}
