package com.example.x_chat.domain.usecase.chat

import com.example.x_chat.domain.model.socket.receive.ChatModel
import com.example.x_chat.domain.model.socket.send.MessageOperationsModel
import com.example.x_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChat (
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: MessageOperationsModel): Flow<ChatModel> {
        return chatRepository.getUerChat(userId)
    }
}