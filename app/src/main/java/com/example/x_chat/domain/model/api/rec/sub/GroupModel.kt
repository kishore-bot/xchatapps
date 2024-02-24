package com.example.x_chat.domain.model.api.rec.sub

data class GroupModel(
    val _id: String,
    val createdAt: String,
    val groupDescription: String?,
    val groupName: String,
    val avatar: String? = "",
    val admin: String
)