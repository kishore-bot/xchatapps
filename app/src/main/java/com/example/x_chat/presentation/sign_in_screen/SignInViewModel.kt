package com.example.x_chat.presentation.sign_in_screen


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.usecase.AppEntryUseCases
import com.example.x_chat.domain.usecase.AuthUseCases
import com.example.x_chat.domain.usecase.SocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val appEntryUseCases: AppEntryUseCases,
    private val socketUseCase: SocketUseCase,
) : ViewModel() {

    private var _state = mutableStateOf(SignInState())

    private val _result = MutableLiveData(false)
    val result: LiveData<Boolean> = _result

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UpdateUser -> {
                _state.value = _state.value.copy(userCredentials = event.userCredentials)
            }

            is SignInEvent.SignInUser -> {
                viewModelScope.launch {
                    userSignUp()
                }

            }

            is SignInEvent.OnSuccess -> {
                navigateToHomeScreen()
            }
        }
    }

    private suspend fun userSignUp() {
        val credentials = _state.value.userCredentials!!
        viewModelScope.launch {
            authUseCases.signInUser(credentials).collect {
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