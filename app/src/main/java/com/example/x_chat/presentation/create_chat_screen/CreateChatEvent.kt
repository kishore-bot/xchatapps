package com.example.x_chat.presentation.create_chat_screen

sealed class CreateChatEvent {
    data class UpdateUserId(val userId: String) : CreateChatEvent()
    data object FetchFriends : CreateChatEvent()
    data object CreatePChat : CreateChatEvent()
    data object Clear : CreateChatEvent()
}