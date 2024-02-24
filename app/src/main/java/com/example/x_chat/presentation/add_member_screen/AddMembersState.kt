package com.example.x_chat.presentation.add_member_screen

import com.example.x_chat.domain.model.api.send.CreateGroupModel

data class AddMembersState(val group: CreateGroupModel? = null)