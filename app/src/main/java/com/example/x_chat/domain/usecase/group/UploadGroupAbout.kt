package com.example.x_chat.domain.usecase.group


import com.example.x_chat.domain.model.api.send.GroupOperations
import com.example.x_chat.domain.repository.GroupRepository
import com.example.x_chat.domain.repository.UserApiRepository

class UploadGroupAbout (
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(about: GroupOperations) {
        groupRepository.uploadGroupAbout(about)
    }
}