package com.example.x_chat.presentation.image_upload_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.rec.ImageUrlModel
import com.example.x_chat.domain.model.socket.send.SendMessagesModel
import com.example.x_chat.domain.usecase.ChatUseCases
import com.example.x_chat.domain.usecase.FileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val fileUseCases: FileUseCases,
    private val chatUseCases: ChatUseCases,
) : ViewModel() {
    private var _state = mutableStateOf(ImageUploadState())

    private val _url = MutableLiveData<ImageUrlModel>()
    val url: MutableLiveData<ImageUrlModel> = _url

    fun onEvent(event: ImageUploadEvent) {
        when (event) {
            is ImageUploadEvent.UpdateFile -> {
                _state.value = _state.value.copy(file = event.file)
            }

            is ImageUploadEvent.UploadChatImage -> {
                uploadChatImage()
            }

            is ImageUploadEvent.UpdateChatId -> {
                _state.value = _state.value.copy(chatId = event.chatId)
            }

            is ImageUploadEvent.UpdateMessage -> {
                _state.value = _state.value.copy(mess = event.mes)
            }

            is ImageUploadEvent.SendMessage -> {
                sendMessage()
            }

            is ImageUploadEvent.UploadProfileImage -> {
                uploadProfileImage()
            }

            is ImageUploadEvent.UploadGroupImage -> {
                uploadGroupImage()
            }

        }
    }

    private fun uploadChatImage() {
        val file = _state.value.file
        viewModelScope.launch {
            if (file != null) {
                fileUseCases.uploadChatImage(file).collect {
                    _url.value = it
                }
            }
        }
    }

    private fun uploadProfileImage() {
        val file = _state.value.file
        viewModelScope.launch {
            if (file != null) {
                fileUseCases.uploadProfileImage(file).collect {
                    _url.value = it
                }
            }
        }
    }

    private fun sendMessage() {
        val mes = SendMessagesModel(
            chatId = _state.value.chatId,
            message = _state.value.mess,
            isFile = true
        )
        viewModelScope.launch {
            chatUseCases.sendMessage(mes).collect()
        }
    }

    private fun uploadGroupImage() {
        val groupId = _state.value.chatId
        val file = _state.value.file
        viewModelScope.launch {
            if (file != null) {
                fileUseCases.uploadGroupImage(file = file, groupId = groupId).collect{
                    _url.value = it
                }
            }
        }
    }
}
