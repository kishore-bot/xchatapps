package com.example.x_chat.domain.usecase.local

import com.example.x_chat.domain.model.api.rec.ImageUrlModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.repository.FileRepository
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class UploadGroupImage(private val fileRepository: FileRepository) {

    suspend operator fun invoke(file: File, groupId: String): Flow<ImageUrlModel> {
        return fileRepository.uploadGroupImage(file = file, groupId = groupId)
    }
}