package com.example.x_chat.data.repository


import android.util.Log
import com.example.x_chat.data.remote.FileApi
import com.example.x_chat.domain.model.api.rec.ImageUrlModel
import com.example.x_chat.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileRepositoryImp(
    private val fileAPi: FileApi
) : FileRepository {
    override suspend fun uploadChatImage(file: File): Flow<ImageUrlModel> {
        return flow {
            val imageType = "image/png".toMediaTypeOrNull()
            val requestBody = file.asRequestBody(imageType)
            val imagePart = MultipartBody.Part.createFormData("picture", file.name, requestBody)
            val imageUrl = fileAPi.uploadChatImage(imagePart)
            emit(imageUrl)
        }.catch { e ->
            e.printStackTrace()
        }
    }

    override suspend fun uploadProfileImage(file: File): Flow<ImageUrlModel> {
        return flow {
            val imageType = "image/png".toMediaTypeOrNull()
            val requestBody = file.asRequestBody(imageType)
            val imagePart = MultipartBody.Part.createFormData("picture", file.name, requestBody)
            val imageUrl = fileAPi.uploadProfileImage(imagePart)
            emit(imageUrl)
        }.catch { e ->
            e.printStackTrace()
        }
    }

    override suspend fun uploadGroupImage(file: File, groupId: String): Flow<ImageUrlModel> {
        Log.d("Test123",groupId)
        return flow {
            val imageType = "image/png".toMediaTypeOrNull()
            val requestBody = file.asRequestBody(imageType)
            val imagePart = MultipartBody.Part.createFormData("picture", file.name, requestBody)
            val imageUrl = fileAPi.uploadGroupImage(image = imagePart, id = groupId)
            emit(imageUrl)
        }.catch { e ->
            e.printStackTrace()
        }
    }
}