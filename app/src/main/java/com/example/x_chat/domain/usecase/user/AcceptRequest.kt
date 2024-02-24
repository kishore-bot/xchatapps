package com.example.x_chat.domain.usecase.user

import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow

class AcceptRequest(private val userApiRepository: UserApiRepository) {

    suspend operator fun invoke(id: String): Flow<ResponseModel> {
        return userApiRepository.acceptRequest(id)
    }
}