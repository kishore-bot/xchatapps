package com.example.x_chat.presentation.sign_in_screen

import com.example.x_chat.domain.model.api.rec.UserCredentials

sealed class SignInEvent {
    data class UpdateUser(val userCredentials: UserCredentials) : SignInEvent()
    data object SignInUser : SignInEvent()
    data object OnSuccess : SignInEvent()
}