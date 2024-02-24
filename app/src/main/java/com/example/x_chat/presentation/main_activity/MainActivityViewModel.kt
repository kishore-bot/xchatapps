package com.example.x_chat.presentation.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.usecase.AppEntryUseCases
import com.example.x_chat.domain.usecase.SocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val socketUseCase: SocketUseCase,
    private val appEntryUseCases: AppEntryUseCases,
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    private val _result = MutableStateFlow(false)
    val result: StateFlow<Boolean> = _result

    init {
        checkToken()
        _isReady.value = true
    }

    private fun checkToken() {
        viewModelScope.launch {
            val token = appEntryUseCases.readAppEntry().firstOrNull()?:""
            if (token == "null") {
                _result.value = false
            } else {
                socketUseCase.connect()
                _result.value = true
            }
        }
    }
}
