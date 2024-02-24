package com.example.x_chat.presentation.chat_screen

import android.app.Application
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.socket.MessageModel
import com.example.x_chat.domain.model.socket.send.MessageOperationsModel
import com.example.x_chat.domain.model.socket.send.SendMessagesModel
import com.example.x_chat.domain.usecase.ChatUseCases
import com.example.x_chat.notification.InAppMessageNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val application: Application
) : ViewModel() {

    private var _state = mutableStateOf(ChatState())

    private val _chat = MutableLiveData<List<MessageModel>?>(emptyList())
    val chatMessage: MutableLiveData<List<MessageModel>?> = _chat

    private val _update = MutableStateFlow("")
    val update = _update.asStateFlow()

    init {
        viewModelScope.launch {
            receiveMessage()
            receiveSeenMessage()
            receiveUnsentMessage()
        }
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.UpdateChatId -> {
                _state.value = _state.value.copy(chatId = event.chatId)
            }

            is ChatEvent.UpdateMessage -> {
                _state.value = _state.value.copy(
                    message = event.message
                )
            }

            is ChatEvent.SendChat -> {
                sendMessage()
            }

            is ChatEvent.UpdateUserId -> {
                _state.value = _state.value.copy(userId = event.userId)
            }

            is ChatEvent.UpdateMessageId -> {
                _state.value = _state.value.copy(mesId = event.messId)
            }

            is ChatEvent.SaveMessage -> {
                saveMessage()
            }

            is ChatEvent.SendUnsentMessage -> {
                sendUnsentMessage()
            }

            is ChatEvent.Group -> {
                _state.value = _state.value.copy(isGroup = event.isGroup)
            }

            is ChatEvent.UpdateReplayId -> {
                _state.value = _state.value.copy(replayId = event.replayId)
            }
        }
    }

    fun clearChatMessages() {
        _chat.value = emptyList()
    }
    fun clearUpdates() {
        _update.value = ""
    }
    suspend fun receiveUserChat() {
        viewModelScope.launch {
            val messageOperationsModel = MessageOperationsModel(
                chatId = _state.value.chatId,
                messageId = "",
                userId = _state.value.userId
            )
            chatUseCases.userChat(messageOperationsModel).collect { userChatData ->
                _chat.value = userChatData.chat
            }
        }
    }

    private fun receiveMessage() {
        viewModelScope.launch {
            chatUseCases.receiveMessage().collect { message ->
                if (message.chatId != _state.value.chatId) {
                    val notification = InAppMessageNotification(
                        context = application,
                        title = message.sender.username,
                        message = message.content
                    )
                    notification.fireNotification()
                } else {
                    _chat.value = _chat.value?.plus(message)
                    sendSeenStatus(message)
                }
            }
        }
    }

    private fun receiveSeenMessage() {
        viewModelScope.launch {
            chatUseCases.receiveSeenMessage().collect { seenMessage ->
                updateSeenStatus(seenMessage)
            }
        }
    }

    private fun receiveUnsentMessage() {
        viewModelScope.launch {
            chatUseCases.receiveUnsentMessage().collect { message ->
                updateUnsentMessage(message)
                delay(200)
                _update.value = "Message Deleted"
            }
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            val chatId = _state.value.chatId
            val mes = _state.value.message
            val replayId = _state.value.replayId
            val sendMes =
                mes?.let {
                    if (replayId == "") {
                        SendMessagesModel(chatId = chatId, message = it)
                    } else {
                        SendMessagesModel(chatId = chatId, message = it, replayId = replayId)
                    }
                }
            if (sendMes != null) {
                chatUseCases.sendMessage(message = sendMes).collect { message ->
                    _chat.value = _chat.value?.plus(message)
                }
            }
        }
    }

    private fun sendSeenStatus(seenMessage: MessageModel) {
        if (seenMessage.sender._id != (_state.value.userId) && !_state.value.isGroup) {
            viewModelScope.launch {
                val chatId = _state.value.chatId
                val mes = MessageOperationsModel(
                    userId = _state.value.userId,
                    chatId = chatId,
                    messageId = seenMessage._id
                )
                chatUseCases.sendSeenMessage(mes)
            }
        }
    }

    private fun saveMessage() {
        viewModelScope.launch {
            val chatId = _state.value.chatId
            val mesId = _state.value.mesId
            val userId = _state.value.userId
            val mes =
                MessageOperationsModel(
                    userId = userId,
                    chatId = chatId,
                    messageId = mesId
                )
            chatUseCases.saveMessage(mes).collect {
                if (it) {
                    _update.value = "Message Saved"
                } else {
                    _update.value = "Message UnSaved"
                }
            }
        }
    }

    private fun sendUnsentMessage() {
        viewModelScope.launch {
            val chatId = _state.value.chatId
            val messId = _state.value.mesId
            val userId = _state.value.userId
            val mes =

                MessageOperationsModel(
                    userId = userId,
                    chatId = chatId,
                    messageId = messId
                )
            chatUseCases.sendUnsentMessage(mes).collect {
                updateUnsentMessage(it)
                delay(500)
                _update.value = "Message Deleted SuccessFully"
            }
        }
    }

    private fun updateUnsentMessage(seenMessage: MessageModel) {
        viewModelScope.launch {
            val updatedChatMessages = _chat.value?.filterNot { message ->
                message._id == seenMessage._id
            }
            _chat.value = updatedChatMessages
        }

    }

    private fun updateSeenStatus(seenMessage: MessageModel) {
        val updatedChatMessages = _chat.value?.map { message ->
            if (message._id == seenMessage._id) {
                message.copy(status = "seen")
            } else {
                message
            }
        }
        _chat.value = updatedChatMessages
    }

}
