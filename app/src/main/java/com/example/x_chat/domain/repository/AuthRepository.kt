package com.example.x_chat.domain.repository

import com.example.x_chat.domain.model.api.rec.TokenModel
import com.example.x_chat.domain.model.api.rec.UserCredentials
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUpUser(userCredentials: UserCredentials):Flow<TokenModel>
    suspend fun signInUser(userCredentials: UserCredentials): Flow<TokenModel>
}