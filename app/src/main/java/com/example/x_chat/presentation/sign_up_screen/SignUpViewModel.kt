package com.example.x_chat.presentation.sign_up_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.usecase.AppEntryUseCases
import com.example.x_chat.domain.usecase.AuthUseCases
import com.example.x_chat.domain.usecase.ChatUseCases
import com.example.x_chat.domain.usecase.SocketUseCase
import com.example.x_chat.presentation.sign_in_screen.SignInEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val appEntryUseCases: AppEntryUseCases,
    private val socketUseCase: SocketUseCase
) : ViewModel() {

    private var _state = mutableStateOf(SignUpState())

    private val _result = MutableLiveData(false)
    val result: LiveData<Boolean> = _result

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UpdateUser -> {
                _state.value = _state.value.copy(userCredentials =  event.userCredentials)
            }

            is SignUpEvent.SignUpUser -> {
                viewModelScope.launch {
                    userSignUp()
                }

            }
            is SignUpEvent.OnSuccess -> {
                navigateToHomeScreen()
            }

        }
    }

    private suspend fun userSignUp() {
        val credentials = _state.value.userCredentials!!
        viewModelScope.launch {
            authUseCases.signUpUser(credentials).collect {
                val token = it.token;
                appEntryUseCases.saveAppEntry(token)
                _result.value = true
            }
        }
    }

    private fun navigateToHomeScreen() {
        viewModelScope.launch {
            socketUseCase.connect()
        }
    }
}