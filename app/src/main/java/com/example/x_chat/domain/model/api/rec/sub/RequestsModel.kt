package com.example.x_chat.domain.model.api.rec.sub

data class RequestsModel(
    val _id: String,
    val createdAt: String,
    val isAccepted: Boolean,
    val senderId: SenderIdModel
)