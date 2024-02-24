package com.example.x_chat.domain.usecase.chat

import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.domain.model.socket.send.MessageOperationsModel
import com.example.x_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SendUnsentMessage(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(message: MessageOperationsModel): Flow<MessageModel> {
        return chatRepository.sentUnsentMessage(message)
    }
}