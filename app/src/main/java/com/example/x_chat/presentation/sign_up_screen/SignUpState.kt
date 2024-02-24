package com.example.x_chat.presentation.sign_up_screen

import com.example.x_chat.domain.model.api.rec.UserCredentials

data class SignUpState(
    val userCredentials: UserCredentials? = null,
)

