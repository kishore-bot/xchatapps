package com.example.x_chat.domain.usecase.user

import com.example.x_chat.domain.model.api.send.AboutModel
import com.example.x_chat.domain.repository.UserApiRepository

class UploadAbout(
    private val userApiRepository: UserApiRepository
) {
    suspend operator fun invoke(about: AboutModel) {
        userApiRepository.uploadAbout(about)
    }
}