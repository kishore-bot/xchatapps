package com.example.x_chat.presentation.search_screen

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
class SearchViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())

    private val _response = MutableStateFlow<ResponseModel?>(null)
    val response: StateFlow<ResponseModel?> = _response

    private val _users = MutableStateFlow<SearchModel?>(null)
    val users: StateFlow<SearchModel?> = _users
    fun oneEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearch -> {
                _state.value = _state.value.copy(user = event.user)
            }

            is SearchEvent.UpdateUserId -> {
                _state.value = _state.value.copy(userId = event.userId)
            }

            is SearchEvent.SearchUser -> {
                searchUser()
            }

            is SearchEvent.SendRequest -> {
                sendRequest()
            }
        }
    }

    private fun searchUser() {
        val user = _state.value.user
        viewModelScope.launch {
            userUseCases.searchUser(user).collect {
                _users.value = it
            }
        }
    }

    private fun sendRequest() {
        val userId = _state.value.userId
        viewModelScope.launch {
            userUseCases.friendRequest(userId).collect {
                _response.value = it
            }
        }
    }
    fun clear(){
        viewModelScope.launch {
            _response.value = null
        }
    }

}