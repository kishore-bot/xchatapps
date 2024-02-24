package com.example.x_chat.data.repository

import android.util.Log
import com.example.x_chat.data.remote.GroupApi
import com.example.x_chat.domain.model.api.rec.GroupInfoModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.model.api.send.CreateGroupModel
import com.example.x_chat.domain.model.api.send.GroupOperations
import com.example.x_chat.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GroupRepositoryImp(
    private val groupApi: GroupApi
) : GroupRepository {

    override suspend fun fetchGChatInfo(chatInfo: ChatInfo): Flow<GroupInfoModel> {
        return flow {
            Log.d("ChatInfo", chatInfo.toString())
            val group = groupApi.fetchGChatInfo(chatInfo)
            Log.d("ChatInfo", group.toString())
            emit(group)
        }
    }

    override suspend fun uploadGroupAbout(about: GroupOperations) {
        groupApi.uploadGroupAbout(about)
    }

    override suspend fun uploadGroupName(name: GroupOperations) {
        groupApi.uploadGroupName(name)
    }

    override suspend fun leaveGroup(chatInfo: ChatInfo): Flow<ResponseModel> {
        return flow { emit(groupApi.leaveGroup(chatInfo)) }
    }


    override suspend fun removeGroup(chatInfo: ChatInfo): Flow<ResponseModel> {
        return flow { emit(groupApi.removeUser(chatInfo)) }
    }

    override suspend fun createGChat(group: CreateGroupModel): Flow<ResponseModel> {
        return flow {
            val users = groupApi.createGChat(group)
            emit(users)
        }
    }


    override suspend fun addMembers(group: CreateGroupModel): Flow<ResponseModel> {
        return flow {
            val users = groupApi.addMembers(group)
            emit(users)
        }
    }

}