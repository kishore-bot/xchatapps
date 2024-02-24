package com.example.x_chat.presentation.account_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.send.AboutModel
import com.example.x_chat.domain.model.api.rec.UserModel
import com.example.x_chat.domain.usecase.AppEntryUseCases
import com.example.x_chat.domain.usecase.UserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private var _state = mutableStateOf(AccountState())

    private val _userModel = MutableStateFlow<UserModel?>(null)
    val userModel: StateFlow<UserModel?> = _userModel

    fun onEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.FetchMe -> {
                fetchMe()
            }

            is AccountEvent.UpdateAbout -> {
                _state.value = _state.value.copy(about = event.about)
            }

            is AccountEvent.UploadAbout -> {
                uploadAbout()
            }

            is AccountEvent.Logout -> {
                logOut()
            }
            is AccountEvent.ClearData ->{
                clearUserValue()
            }
        }
    }

    private fun fetchMe() {
        viewModelScope.launch {
            userUseCases.fetchMe().collect {
                _userModel.value = it
            }
        }
    }

    private fun uploadAbout() {
        viewModelScope.launch {
            val about = _state.value.about?.let {
                AboutModel(
                    about = it
                )
            }
            if (about != null) {
                userUseCases.uploadAbout(about)
            }
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            appEntryUseCases.deleteAppEntry()
        }
    }

    private fun clearUserValue() {
        viewModelScope.launch {
            _userModel.value = null
        }
    }
}
