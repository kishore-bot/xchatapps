package com.example.x_chat.presentation.g_chat_info_screen


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x_chat.domain.model.api.rec.GroupInfoModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.send.GroupOperations
import com.example.x_chat.domain.usecase.GroupUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GChatInfoViewModel @Inject constructor(
    private val groupUserCase: GroupUserCase
) : ViewModel() {

    private val _state = mutableStateOf(GChatInfoState())

    private val _result = MutableStateFlow<ResponseModel?>(null)
    val result: StateFlow<ResponseModel?> = _result

    private var _groupModel = MutableStateFlow<GroupInfoModel?>(null)
    val groupModel: StateFlow<GroupInfoModel?> = _groupModel


    fun onEvent(event: GChatInfoEvent) {
        when (event) {
            is GChatInfoEvent.UpdateChatInfo -> {
                _state.value = _state.value.copy(chatInfo = event.chatInfo)
            }

            is GChatInfoEvent.UpdateAbout -> {
                _state.value = _state.value.copy(about = event.about)
            }

            is GChatInfoEvent.UpdateName -> {
                _state.value = _state.value.copy(name = event.name)
            }

            is GChatInfoEvent.UpdateGroupId -> {
                _state.value = _state.value.copy(groupId = event.groupId)
            }

            is GChatInfoEvent.UploadAbout -> {
                uploadAbout()
            }


            is GChatInfoEvent.UploadName -> {
                uploadName()
            }

            is GChatInfoEvent.FetchDetails -> {
                fetchDetails()
            }

            is GChatInfoEvent.RemoveUser -> {
                removeUser()
            }

            is GChatInfoEvent.LeaveGroup -> {
                leaveGroup()
            }

            is GChatInfoEvent.Clear -> {
                clear()
            }
        }
    }


    private fun fetchDetails() {
        val chatInfo = _state.value.chatInfo
        viewModelScope.launch {
            if (chatInfo != null) {
                groupUserCase.fetchGChatInfo(chatInfo).collect {
                    _groupModel.value = it
                }
            }
        }
    }

    private fun uploadName() {
        val groupId = _state.value.groupId
        val name = _state.value.name
        val group = GroupOperations(
            groupId = groupId,
            name = name
        )
        viewModelScope.launch {
            groupUserCase.uploadGroupName(group)
        }
    }

    private fun uploadAbout() {
        val groupId = _state.value.groupId
        val about = _state.value.about
        val group = GroupOperations(
            groupId = groupId,
            about = about
        )
        viewModelScope.launch {
            groupUserCase.uploadGroupAbout(group)
        }
    }

    private fun leaveGroup() {
        val group = _state.value.chatInfo
        viewModelScope.launch {
            if (group != null) {
                groupUserCase.leaveGroup(group).collect {
                    _result.value = it
                }
            }
        }
    }

    private fun removeUser() {
        val group = _state.value.chatInfo
        viewModelScope.launch {
            if (group != null) {
                groupUserCase.removeUser(group).collect {
                    val userIdToRemove = it.message
                    val currentState = _groupModel.value ?: return@collect
                    val updatedOwners = currentState.owners.toMutableList()
                    val iterator = updatedOwners.iterator()
                    while (iterator.hasNext()) {
                        val owner = iterator.next()
                        if (owner._id == userIdToRemove) {
                            iterator.remove()
                            break
                        }
                    }
                    _groupModel.value = currentState.copy(owners = updatedOwners)
                }
            }
        }
    }

    private fun clear() {
        viewModelScope.launch {
            _groupModel.value = null
        }
    }
}