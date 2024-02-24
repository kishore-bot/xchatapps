package com.example.x_chat.presentation.g_chat_info_screen

import com.example.x_chat.domain.model.api.send.ChatInfo

sealed  class GChatInfoEvent {
    data class UpdateChatInfo(val chatInfo: ChatInfo):GChatInfoEvent()
    data class UpdateName(val name: String):GChatInfoEvent()
    data class UpdateAbout(val about: String):GChatInfoEvent()
    data class UpdateGroupId(val groupId: String):GChatInfoEvent()

    data object FetchDetails:GChatInfoEvent()
    data object UploadName:GChatInfoEvent()
    data object UploadAbout:GChatInfoEvent()
    data object RemoveUser:GChatInfoEvent()
    data object LeaveGroup:GChatInfoEvent()
    data object Clear:GChatInfoEvent()

}