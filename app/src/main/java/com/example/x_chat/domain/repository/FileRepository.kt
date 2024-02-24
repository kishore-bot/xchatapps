package com.example.x_chat.domain.repository

import com.example.x_chat.domain.model.api.rec.ImageUrlModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileRepository {
    suspend fun uploadChatImage(file: File): Flow<ImageUrlModel>
    suspend fun uploadProfileImage(file: File): Flow<ImageUrlModel>
    suspend fun uploadGroupImage(file: File, groupId: String): Flow<ImageUrlModel>
}