package com.example.x_chat.domain.usecase

import com.example.x_chat.domain.usecase.chat.GetChat
import com.example.x_chat.domain.usecase.chat.ReceiveMessage
import com.example.x_chat.domain.usecase.chat.ReceiveSeenMessage
import com.example.x_chat.domain.usecase.chat.ReceiveUnsentMessage
import com.example.x_chat.domain.usecase.chat.SaveMessage
import com.example.x_chat.domain.usecase.chat.SendMessage
import com.example.x_chat.domain.usecase.chat.SendSeenMessage
import com.example.x_chat.domain.usecase.chat.SendUnsentMessage

data class ChatUseCases(
    val sendMessage: SendMessage,
    val receiveMessage: ReceiveMessage,
    val userChat: GetChat,
    val receiveSeenMessage: ReceiveSeenMessage,
    val sendSeenMessage: SendSeenMessage,
    val saveMessage: SaveMessage,
    val sendUnsentMessage: SendUnsentMessage,
    val receiveUnsentMessage: ReceiveUnsentMessage
)
