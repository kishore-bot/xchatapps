package com.example.x_chat.domain.usecase.user

import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow

class SearchUser(private val userApiRepository: UserApiRepository) {

    suspend operator fun invoke(user: String): Flow<SearchModel> {
        return userApiRepository.searchUser(user)
    }
}