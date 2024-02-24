package com.example.x_chat.data.remote

import com.example.x_chat.domain.model.api.rec.ImageUrlModel
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FileApi {
    @Multipart
    @POST("file/chat")
    suspend fun uploadChatImage(
        @Part image: MultipartBody.Part
    ): ImageUrlModel

    @Multipart
    @POST("file/profile")
    suspend fun uploadProfileImage(
        @Part image: MultipartBody.Part
    ): ImageUrlModel

    @Multipart
    @POST("file/group/{id}")
    suspend fun uploadGroupImage(
        @Path("id") id: String,
        @Part image: MultipartBody.Part,
    ): ImageUrlModel
}