package com.example.x_chat.presentation.in_app_notification_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.rec.ReceivedReqModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InAppNotificationViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _state = mutableStateOf(InAppNotificationState())

    private val _notifications = MutableStateFlow<ReceivedReqModel?>(null)
    val notifications: StateFlow<ReceivedReqModel?> = _notifications

    private val _response = MutableStateFlow<ResponseModel?>(null)
    val response: StateFlow<ResponseModel?> = _response

    fun oneEvent(event: InAppNotificationEvent) {
        when (event) {
            is InAppNotificationEvent.UpdateUserId -> {
                _state.value = _state.value.copy(userId = event.userId)
            }

            is InAppNotificationEvent.Fetch -> {
                fetchUser()
            }

            is InAppNotificationEvent.Clear -> {
                _response.value = null
            }

            is InAppNotificationEvent.Accept -> {
                accept()
            }

        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            userUseCases.fetchNotification().collect {
                _notifications.value = it
            }
        }
    }

    private fun accept() {
        val userId = _state.value.userId
        viewModelScope.launch {
            userUseCases.acceptRequest(userId).collect {
                _response.value = it
            }
        }
    }
}