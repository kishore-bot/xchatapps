package com.example.x_chat.domain.usecase.user

import com.example.x_chat.domain.model.api.rec.ReceivedReqModel
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow

class FetchNotification (private val userApiRepository: UserApiRepository) {

    suspend operator fun invoke(): Flow<ReceivedReqModel> {
        return userApiRepository.fetchNotification()
    }
}