package com.example.x_chat.presentation.p_chat_info_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.rec.UserModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PChatViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _userModel = MutableStateFlow<UserModel?>(null)
    val userModel: StateFlow<UserModel?> = _userModel
    fun fetch(chatInfo: ChatInfo) {
        Log.d("TestIng2","From V V $chatInfo")
        viewModelScope.launch {
            userUseCases.fetchPChatInfo(chatInfo).collect {

                _userModel.value = it
            }
        }
    }
}