package com.example.x_chat.domain.usecase

import com.example.x_chat.domain.usecase.auth.SignInUser
import com.example.x_chat.domain.usecase.auth.SignUpUser

data class AuthUseCases(
    val signUpUser: SignUpUser,
    val signInUser: SignInUser
)