package com.example.x_chat.presentation.image_upload_screen

import java.io.File


data class ImageUploadState(
    val file: File? = null,
    val chatId: String = "",
    val mess: String = ""
)