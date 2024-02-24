package com.example.x_chat.presentation.image_upload_screen

import java.io.File

sealed class ImageUploadEvent {
    data class UpdateFile(val file: File) : ImageUploadEvent()
    data class UpdateChatId(val chatId: String) : ImageUploadEvent()
    data class UpdateMessage(val mes: String) : ImageUploadEvent()

    data object UploadChatImage : ImageUploadEvent()
    data object UploadProfileImage : ImageUploadEvent()
    data object UploadGroupImage : ImageUploadEvent()
    data object SendMessage : ImageUploadEvent()
}