package com.example.x_chat.domain.usecase.user

import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow

class FetchAllFriends(private val userApiRepository: UserApiRepository) {

    suspend operator fun invoke(): Flow<SearchModel> {
        return userApiRepository.getFriends()
    }
}