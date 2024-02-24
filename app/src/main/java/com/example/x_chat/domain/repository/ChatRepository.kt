package com.example.x_chat.domain.repository

import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.domain.model.socket.receive.ChatModel
import com.example.x_chat.domain.model.socket.send.MessageOperationsModel
import com.example.x_chat.domain.model.socket.send.SendMessagesModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessage(sendMessage: SendMessagesModel): Flow<MessageModel>
    suspend fun getUerChat(userId: MessageOperationsModel): Flow<ChatModel>
    suspend fun receiveMessage(): Flow<MessageModel>
    suspend fun receiveSeenMessage(): Flow<MessageModel>
    suspend fun sendSeenMessage(mes: MessageOperationsModel)
    suspend fun saveMessage(mes: MessageOperationsModel): Flow<Boolean>
    suspend fun sentUnsentMessage(mes: MessageOperationsModel): Flow<MessageModel>
    suspend fun receiveUnsentMessage(): Flow<MessageModel>
}