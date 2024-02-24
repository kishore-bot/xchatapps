package com.example.x_chat.domain.usecase.group

import com.example.x_chat.domain.model.api.rec.GroupInfoModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.repository.GroupRepository
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow

class FetchGChatInfo (private val groupRepository: GroupRepository) {

    suspend operator fun invoke(chatInfo: ChatInfo): Flow<GroupInfoModel> {
        return groupRepository.fetchGChatInfo(chatInfo)
    }
}