package com.example.x_chat.data.remote

import com.example.x_chat.domain.model.api.rec.ReceivedReqModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.model.api.send.AboutModel
import com.example.x_chat.domain.model.api.rec.UserModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.utils.Constants.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("${User}me")
    suspend fun fetchMe(): UserModel

    @POST("${User}p_chat")
    suspend fun fetchPChatInfo(
        @Body chatInfo: ChatInfo
    ): UserModel

    @POST("${User}about")
    suspend fun uploadAbout(
        @Body about: AboutModel
    )

    @POST("${User}search")
    suspend fun searchUser(
        @Query("user") user: String
    ): SearchModel

    @GET("${User}friends")
    suspend fun getFriends(
    ): SearchModel

    @POST("${User}personal/{id}")
    suspend fun createPChat(
        @Path("id") id: String,
    ): ResponseModel

    @POST("${User}request/{id}")
    suspend fun friendRequest(
        @Path("id") id: String,
    ): ResponseModel

    @GET("${User}received/req")
    suspend fun fetchNotifications(
    ): ReceivedReqModel

    @POST("${User}accept/{id}")
    suspend fun acceptReq(
        @Path("id") id: String
    ): ResponseModel
}