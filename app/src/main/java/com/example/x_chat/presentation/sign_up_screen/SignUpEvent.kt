package com.example.x_chat.presentation.sign_up_screen

import com.example.x_chat.domain.model.api.rec.UserCredentials

sealed class SignUpEvent {
    data class UpdateUser(val userCredentials: UserCredentials) : SignUpEvent()
    data object SignUpUser : SignUpEvent()
    data object OnSuccess : SignUpEvent()
}