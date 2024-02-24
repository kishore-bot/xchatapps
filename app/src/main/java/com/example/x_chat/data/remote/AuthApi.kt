package com.example.x_chat.data.remote

import com.example.x_chat.domain.model.api.rec.TokenModel
import com.example.x_chat.domain.model.api.rec.UserCredentials
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/signUp")
    suspend fun signUpUser(
        @Body subTask: UserCredentials,
    ): TokenModel

    @POST("auth/signIn")
    suspend fun signInUser(
        @Body subTask: UserCredentials,
    ): TokenModel
}