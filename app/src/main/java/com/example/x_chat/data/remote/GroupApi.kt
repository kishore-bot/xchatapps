package com.example.x_chat.data.remote

import com.example.x_chat.domain.model.api.rec.GroupInfoModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.model.api.send.CreateGroupModel
import com.example.x_chat.domain.model.api.send.GroupOperations
import com.example.x_chat.utils.Constants
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface GroupApi {
    @POST("${Constants.Group}groupInfo")
    suspend fun fetchGChatInfo(
        @Body chatInfo: ChatInfo
    ): GroupInfoModel

    @POST("${Constants.Group}name")
    suspend fun uploadGroupName(
        @Body name: GroupOperations
    )

    @POST("${Constants.Group}description")
    suspend fun uploadGroupAbout(
        @Body about: GroupOperations
    )

    @PATCH("${Constants.Group}leave")
    suspend fun leaveGroup(
        @Body about: ChatInfo
    ): ResponseModel

    @PATCH("${Constants.Group}remove")
    suspend fun removeUser(
        @Body about: ChatInfo
    ): ResponseModel

    @POST("${Constants.Group}create")
    suspend fun createGChat(
        @Body group: CreateGroupModel,
    ): ResponseModel

    @POST("${Constants.Group}members")
    suspend fun addMembers(
        @Body group: CreateGroupModel,
    ): ResponseModel
}