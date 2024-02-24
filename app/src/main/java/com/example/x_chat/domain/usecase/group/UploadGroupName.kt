package com.example.x_chat.domain.usecase.group


import com.example.x_chat.domain.model.api.send.GroupOperations
import com.example.x_chat.domain.repository.GroupRepository
import com.example.x_chat.domain.repository.UserApiRepository

class UploadGroupName (
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(name: GroupOperations) {
        groupRepository.uploadGroupName(name)
    }
}