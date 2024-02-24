package com.example.x_chat.domain.model.api.rec

data class UserModel(
    val __v: Int,
    val _id: String,
    val createAt: String,
    val email: String,
    val phoneNo: Long,
    val username: String,
    val about: String? = "",
    val avatar: String = "",
)