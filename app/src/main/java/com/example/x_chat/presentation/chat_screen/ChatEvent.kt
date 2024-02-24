package com.example.x_chat.presentation.chat_screen

sealed class ChatEvent {
    data class UpdateMessage(val message: String) : ChatEvent()
    data class UpdateChatId(val chatId: String) : ChatEvent()
    data class UpdateUserId(val userId: String) : ChatEvent()
    data class UpdateMessageId(val messId: String) : ChatEvent()
    data class Group(val isGroup: Boolean) : ChatEvent()
    data class UpdateReplayId(val replayId: String) : ChatEvent()

    data object SendChat : ChatEvent()
    data object SaveMessage : ChatEvent()
    data object SendUnsentMessage : ChatEvent()
}