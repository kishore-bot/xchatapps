package com.example.x_chat.presentation.chat_screen

data class ChatState(
    val message: String? = "",
    val chatId:String="",
    val userId: String = "",
    val mesId: String ="",
    val replayId: String ="",
    val isGroup: Boolean = false,
)