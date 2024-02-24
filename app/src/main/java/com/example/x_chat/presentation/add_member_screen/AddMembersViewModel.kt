package com.example.x_chat.presentation.add_member_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.usecase.GroupUserCase
import com.example.x_chat.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMembersViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val groupUserCase: GroupUserCase
) : ViewModel() {

    private val _state = mutableStateOf(AddMembersState())

    private val _response = MutableStateFlow<ResponseModel?>(null)
    val response: StateFlow<ResponseModel?> = _response

    private val _users = MutableStateFlow<SearchModel?>(null)
    val users: StateFlow<SearchModel?> = _users

    fun oneEvent(event: AddMembersEvent) {
        when (event) {
            is AddMembersEvent.UpdateGroup -> {
                _state.value = _state.value.copy(group = event.group)
            }

            is AddMembersEvent.FetchFriends -> {
                fetchUser()

            }
            is AddMembersEvent.AddMembers ->{
                addMembers()
            }

        }
    }

    private fun addMembers() {
        val group = _state.value.group
        viewModelScope.launch {
            if (group != null) {
                groupUserCase.addMembers(group).collect {
                    _response.value = it
                }
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
}