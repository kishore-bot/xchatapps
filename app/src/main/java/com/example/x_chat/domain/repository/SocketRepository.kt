package com.example.x_chat.domain.repository

import com.example.x_chat.domain.model.socket.receive.HomeChatList
import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    suspend fun connection()
    suspend fun getHomeChatList(): Flow<HomeChatList>
}