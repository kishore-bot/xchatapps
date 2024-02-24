package com.example.x_chat.presentation.sign_in_screen

import com.example.x_chat.domain.model.api.rec.UserCredentials

data class SignInState(
    val userCredentials: UserCredentials? = null,
)
