package com.example.x_chat.presentation.create_chat_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _state = mutableStateOf(CreateChatState())

    private val _users = MutableStateFlow<SearchModel?>(null)
    val users: StateFlow<SearchModel?> = _users

    private val _response = MutableStateFlow<ResponseModel?>(null)
    val response: StateFlow<ResponseModel?> = _response

    fun oneEvent(event: CreateChatEvent) {
        when (event) {
            is CreateChatEvent.UpdateUserId -> {
                _state.value = _state.value.copy(userId = event.userId)
            }

            is CreateChatEvent.FetchFriends -> {
                fetchUser()
            }

            is CreateChatEvent.CreatePChat -> {
                createPChat()
            }

            is CreateChatEvent.Clear -> {
                _response.value = null
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            userUseCases.fetchAllFriends().collect {
                _users.value = it
            }
        }
    }

    private fun createPChat() {
        val userId = _state.value.userId
        viewModelScope.launch {
            if (userId != null) {
                userUseCases.createPChat(userId).collect {
                    _response.value = it
                }
            }
        }
    }
}