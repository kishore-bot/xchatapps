package com.example.x_chat.data.repository

import android.util.Log
import com.example.x_chat.domain.model.socket.receive.HomeChatList
import com.example.x_chat.domain.repository.SocketRepository
import io.socket.client.Ack
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class SocketRepositoryImp(
    private val socket: Socket
) : SocketRepository {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    override suspend fun connection() {
        socket.connect()
        try {
            socket.on(Socket.EVENT_CONNECT) {
            }
        } catch (e: Exception) {
            Log.e("SocketIO", "Connection error:", e)
        }
    }

    override suspend fun getHomeChatList(): Flow<HomeChatList> = callbackFlow {
        socket.emit("get-chat-list", Ack { args ->
            val stringToJson = args[0].toString()

            val homeChatModel = json.decodeFromString<HomeChatList>(stringToJson)
            trySend(homeChatModel)
        })
        awaitClose {
            socket.off("get-chat-list")
        }
    }.flowOn(Dispatchers.IO)
}