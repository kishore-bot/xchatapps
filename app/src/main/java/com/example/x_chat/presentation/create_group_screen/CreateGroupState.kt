package com.example.x_chat.presentation.create_group_screen

import com.example.x_chat.domain.model.api.send.CreateGroupModel

data class CreateGroupState(
    val group: CreateGroupModel? = null
)