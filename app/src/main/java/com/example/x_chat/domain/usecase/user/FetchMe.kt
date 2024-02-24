package com.example.x_chat.domain.usecase.user

import com.example.x_chat.domain.model.api.rec.UserModel
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow

class FetchMe(private val userApiRepository: UserApiRepository) {

    suspend operator fun invoke(): Flow<UserModel> {
        return userApiRepository.fetchMe()
    }
}