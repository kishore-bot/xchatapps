package com.example.x_chat.presentation.create_group_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.usecase.GroupUserCase
import com.example.x_chat.domain.usecase.UserUseCases
import com.example.x_chat.presentation.create_chat_screen.CreateChatEvent
import com.example.x_chat.presentation.create_chat_screen.CreateChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupUserCase: GroupUserCase
) : ViewModel() {

    private val _state = mutableStateOf(CreateGroupState())

    private val _response = MutableStateFlow<ResponseModel?>(null)
    val response: StateFlow<ResponseModel?> = _response

    fun oneEvent(event: CreateGroupEvent) {
        when (event) {
            is CreateGroupEvent.UpdateGroup -> {
                _state.value = _state.value.copy(group = event.group)
            }

            is CreateGroupEvent.CreateGroup -> {
                createGChat()
            }

        }
    }

    private fun createGChat() {
        val group = _state.value.group
        viewModelScope.launch {
            if (group != null) {
                groupUserCase.createGroup(group).collect {
                    _response.value = it
                }
            }
        }
    }
}