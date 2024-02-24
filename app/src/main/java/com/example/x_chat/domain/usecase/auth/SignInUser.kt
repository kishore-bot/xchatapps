package com.example.x_chat.domain.usecase.auth

import com.example.x_chat.domain.model.api.rec.TokenModel
import com.example.x_chat.domain.model.api.rec.UserCredentials
import com.example.x_chat.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow


class SignInUser(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userCredentials: UserCredentials): Flow<TokenModel> {
        return authRepository.signInUser(userCredentials)
    }
}