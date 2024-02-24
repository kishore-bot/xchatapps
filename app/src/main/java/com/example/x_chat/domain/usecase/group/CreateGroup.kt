package com.example.x_chat.domain.usecase.group

import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.send.CreateGroupModel
import com.example.x_chat.domain.repository.GroupRepository
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow

class CreateGroup(private val groupRepository: GroupRepository) {

    suspend operator fun invoke(group: CreateGroupModel): Flow<ResponseModel> {
        return groupRepository.createGChat(group)
    }
}