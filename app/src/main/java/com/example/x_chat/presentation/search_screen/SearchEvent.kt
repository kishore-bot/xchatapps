package com.example.x_chat.presentation.search_screen

sealed class SearchEvent {
    data class UpdateSearch(val user: String) : SearchEvent()
    data class UpdateUserId(val userId:  String) : SearchEvent()
    data object SearchUser : SearchEvent()
    data object SendRequest : SearchEvent()
}