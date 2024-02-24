package com.example.x_chat.presentation.account_screen

sealed class AccountEvent {
    data class UpdateAbout(val about: String) : AccountEvent()

    data object FetchMe : AccountEvent()
    data object UploadAbout : AccountEvent()
    data object Logout : AccountEvent()
    data object  ClearData: AccountEvent()
}