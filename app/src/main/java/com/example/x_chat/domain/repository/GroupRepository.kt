package com.example.x_chat.domain.repository

import com.example.x_chat.domain.model.api.rec.GroupInfoModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.model.api.send.CreateGroupModel
import com.example.x_chat.domain.model.api.send.GroupOperations
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun fetchGChatInfo(chatInfo: ChatInfo): Flow<GroupInfoModel>
    suspend fun uploadGroupAbout(about: GroupOperations)
    suspend fun uploadGroupName(name: GroupOperations)
    suspend fun leaveGroup(chatInfo: ChatInfo): Flow<ResponseModel>
    suspend fun removeGroup(chatInfo: ChatInfo): Flow<ResponseModel>
    suspend fun createGChat(group: CreateGroupModel):Flow<ResponseModel>
    suspend fun addMembers(group: CreateGroupModel):Flow<ResponseModel>
}