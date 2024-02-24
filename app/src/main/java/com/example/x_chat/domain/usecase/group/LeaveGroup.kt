package com.example.x_chat.domain.usecase.group


import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.repository.GroupRepository
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow


class LeaveGroup (private val groupRepository: GroupRepository) {
    suspend operator fun invoke(chatInfo: ChatInfo): Flow<ResponseModel> {
        return groupRepository.leaveGroup(chatInfo)
    }
}