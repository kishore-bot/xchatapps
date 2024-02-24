package com.example.x_chat.presentation.home_screen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.socket.receive.HomeChatList
import com.example.x_chat.domain.usecase.ChatUseCases
import com.example.x_chat.domain.usecase.SocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val socketUseCase: SocketUseCase,
    private val chatUseCases: ChatUseCases,
) : ViewModel() {

    private val _chatMessages = MutableLiveData<HomeChatList>()
    val chatMessages: MutableLiveData<HomeChatList> get() = _chatMessages

    init {
        viewModelScope.launch {
            chatUseCases.receiveMessage().collect{
                getHomeScreenChat()
            }
        }
    }

    fun getHomeScreenChat() {
        viewModelScope.launch {
            socketUseCase.getHomeChat().collect { messages ->
                _chatMessages.value = messages

            }
        }
    }
}
