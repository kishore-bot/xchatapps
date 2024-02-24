package com.example.x_chat.data.repository

import android.util.Log
import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.domain.model.socket.receive.ChatModel
import com.example.x_chat.domain.model.socket.send.MessageOperationsModel
import com.example.x_chat.domain.model.socket.send.SendMessagesModel
import com.example.x_chat.domain.repository.ChatRepository
import io.socket.client.Ack
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChatRepositoryImp(
    private val socket: Socket
) : ChatRepository {
    private  val json = Json {
        ignoreUnknownKeys = true
    }
    override suspend fun sendMessage(sendMessage: SendMessagesModel): Flow<MessageModel> = callbackFlow {
        val jsonString = Json.encodeToString(sendMessage)
        socket.emit("send-message", jsonString, Ack { args ->
            val stringToJson = args[0].toString()
            val message: MessageModel = json.decodeFromString(stringToJson)
            trySend(message)
        })

        awaitClose {
            socket.off("send-message")
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getUerChat(userId: MessageOperationsModel): Flow<ChatModel> = callbackFlow {
        val id = json.encodeToString(userId)
        socket.emit("get-chat", id, Ack { args ->
            val stringToJson = args[0].toString()
            val message: ChatModel = json.decodeFromString(stringToJson)
            trySend(message)
        })
        awaitClose {
            socket.off("get-chat")
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun receiveMessage(): Flow<MessageModel> = callbackFlow {
        val callback = Emitter.Listener { args ->
            launch {
                val jsonString = args[0].toString()
                val message: MessageModel = json.decodeFromString(jsonString)
                try {
                    trySendBlocking(message)
                } catch (e: ClosedSendChannelException) {
                    Log.e("SocketIO", "Failed to send message:", e)
                }
            }
        }
        socket.on("receive-message", callback)
        awaitClose {
            socket.off("receive-message", callback)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun receiveSeenMessage(): Flow<MessageModel> = callbackFlow {
        val callback = Emitter.Listener { args ->
            launch {
                val jsonString = args[0].toString()
                val message: MessageModel = json.decodeFromString(jsonString)
                try {
                    trySendBlocking(message)
                } catch (e: ClosedSendChannelException) {
                    Log.e("SocketIO", "Failed to send message:", e)
                }
            }
        }
        socket.on("seen-message", callback)
        awaitClose {
            socket.off("seen-message", callback)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun sendSeenMessage(mes: MessageOperationsModel) {
        val jsonMes = json.encodeToString(mes)
        socket.emit("seen-message", jsonMes)
    }

    override suspend fun saveMessage(mes: MessageOperationsModel): Flow<Boolean> = callbackFlow {
        val id = Json.encodeToString(mes)
        socket.emit("save-message", id, Ack { args ->
            val stringToJson = args[0].toString()
            val saved: Boolean = json.decodeFromString(stringToJson)
            trySend(saved)
        })
        awaitClose {
            socket.off("save-message")
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun sentUnsentMessage(mes: MessageOperationsModel): Flow<MessageModel> = callbackFlow {
        val jsonString = Json.encodeToString(mes)
        socket.emit("unsent-message", jsonString, Ack { args ->
            val stringToJson = args[0].toString()
            Log.d("Unsent",stringToJson)
            val message: MessageModel = json.decodeFromString(stringToJson)
            trySend(message)
        })

        awaitClose {
            socket.off("unsent-message")
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun receiveUnsentMessage(): Flow<MessageModel> = callbackFlow {
        val callback = Emitter.Listener { args ->
            launch {
                val jsonString = args[0].toString()
                val message: MessageModel = json.decodeFromString(jsonString)
                try {
                    trySendBlocking(message)
                } catch (e: ClosedSendChannelException) {
                    Log.e("SocketIO", "Failed to send message:", e)
                }
            }
        }
        socket.on("unsent-message", callback)
        awaitClose {
            socket.off("unsent-message", callback)
        }
    }.flowOn(Dispatchers.IO)
}
