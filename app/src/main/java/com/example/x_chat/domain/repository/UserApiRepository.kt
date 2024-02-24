package com.example.x_chat.domain.repository

import com.example.x_chat.domain.model.api.rec.ReceivedReqModel
import com.example.x_chat.domain.model.api.send.AboutModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.model.api.rec.UserModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import kotlinx.coroutines.flow.Flow

interface UserApiRepository {
    suspend fun fetchMe(): Flow<UserModel>
    suspend fun uploadAbout(about: AboutModel)
    suspend fun fetchPChatInfo(chatInfo: ChatInfo): Flow<UserModel>
    suspend fun searchUser(user: String): Flow<SearchModel>
    suspend fun getFriends(): Flow<SearchModel>
    suspend fun createPChat(userId:String):Flow<ResponseModel>
    suspend fun friendRequest(id:String):Flow<ResponseModel>
    suspend fun acceptRequest(id:String):Flow<ResponseModel>
    suspend fun fetchNotification():Flow<ReceivedReqModel>

}