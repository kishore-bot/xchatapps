package com.example.x_chat.presentation.create_group_screen

import com.example.x_chat.domain.model.api.send.CreateGroupModel

sealed class CreateGroupEvent {
        data class UpdateGroup(val group: CreateGroupModel) : CreateGroupEvent()
        data object CreateGroup : CreateGroupEvent()
}