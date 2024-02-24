package com.example.x_chat.presentation.g_chat_info_screen

import com.example.x_chat.domain.model.api.send.ChatInfo

data class GChatInfoState(
    val about: String = "",
    val chatInfo: ChatInfo? = null,
    val groupId: String = "",
    val name: String = ""
)