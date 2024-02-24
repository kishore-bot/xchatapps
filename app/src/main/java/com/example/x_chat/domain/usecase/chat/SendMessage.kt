package com.example.x_chat.domain.usecase.chat

import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.domain.model.socket.send.SendMessagesModel
import com.example.x_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SendMessage  (private val chatRepository: ChatRepository) {
    suspend operator fun invoke(message: SendMessagesModel): Flow<MessageModel> {
        return chatRepository.sendMessage(message)
    }
}
