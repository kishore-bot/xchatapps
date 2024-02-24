package com.example.x_chat.presentation.add_member_screen

import com.example.x_chat.domain.model.api.send.CreateGroupModel

sealed class AddMembersEvent {
    data class UpdateGroup(val group: CreateGroupModel) : AddMembersEvent()
    data object FetchFriends : AddMembersEvent()
    data object AddMembers : AddMembersEvent()
}
