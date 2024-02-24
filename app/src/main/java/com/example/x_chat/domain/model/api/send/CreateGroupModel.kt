package com.example.x_chat.domain.model.api.send

import com.example.x_chat.domain.model.api.send.sub.UserIdModel

data class CreateGroupModel(
    val groupDesc: String? = null,
    val groupName: String,
    val userId: List<UserIdModel>
)