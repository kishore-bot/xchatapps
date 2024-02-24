package com.example.x_chat.data.repository

import com.example.x_chat.data.remote.AuthApi
import com.example.x_chat.domain.model.api.rec.TokenModel
import com.example.x_chat.domain.model.api.rec.UserCredentials
import com.example.x_chat.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImp(
    private val authApi: AuthApi,
) : AuthRepository {
    override suspend fun signUpUser(userCredentials: UserCredentials): Flow<TokenModel> {
        return flow {
            emit(authApi.signUpUser(userCredentials))
        }
    }

    override suspend fun signInUser(userCredentials: UserCredentials): Flow<TokenModel> {
        return flow {
            emit(authApi.signInUser(userCredentials))
        }
    }
}