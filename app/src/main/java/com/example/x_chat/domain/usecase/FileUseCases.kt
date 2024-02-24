package com.example.x_chat.domain.usecase

import com.example.x_chat.domain.usecase.file.UploadChatImage
import com.example.x_chat.domain.usecase.file.UploadProfileImage
import com.example.x_chat.domain.usecase.local.UploadGroupImage

data class FileUseCases (
    val uploadChatImage: UploadChatImage,
    val uploadProfileImage: UploadProfileImage,
    val uploadGroupImage: UploadGroupImage
)