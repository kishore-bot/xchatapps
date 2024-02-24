package com.example.x_chat.domain.usecase.file

import com.example.x_chat.domain.model.api.rec.ImageUrlModel
import com.example.x_chat.domain.repository.FileRepository
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class UploadChatImage(private val fileRepository: FileRepository) {

    suspend operator fun invoke(file: File): Flow<ImageUrlModel> {
        return fileRepository.uploadChatImage(file)
    }
}