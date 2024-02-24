package com.example.x_chat.domain.model.api.rec

data class UserCredentials(
    val email: String,
    val password: String,
    val username: String,
    val phoneNo: Number,
)