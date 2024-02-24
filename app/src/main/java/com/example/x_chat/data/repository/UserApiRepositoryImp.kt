package com.example.x_chat.data.repository


import com.example.x_chat.data.remote.UserApi
import com.example.x_chat.domain.model.api.rec.ReceivedReqModel
import com.example.x_chat.domain.model.api.rec.ResponseModel
import com.example.x_chat.domain.model.api.rec.SearchModel
import com.example.x_chat.domain.model.api.send.AboutModel
import com.example.x_chat.domain.model.api.rec.UserModel
import com.example.x_chat.domain.model.api.send.ChatInfo
import com.example.x_chat.domain.repository.UserApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UserApiRepositoryImp(
    private val userApi: UserApi
) : UserApiRepository {

    override suspend fun fetchMe(): Flow<UserModel> {
        return flow {
            val user = userApi.fetchMe()
            emit(user)
        }
    }

    override suspend fun uploadAbout(about: AboutModel) {
        userApi.uploadAbout(about)
    }

    override suspend fun fetchPChatInfo(chatInfo: ChatInfo): Flow<UserModel> {
        return flow {
            val user = userApi.fetchPChatInfo(chatInfo)
            emit(user)
        }
    }

    override suspend fun searchUser(user: String): Flow<SearchModel> {
        return flow {
            val users = userApi.searchUser(user)
            emit(users)
        }
    }

    override suspend fun getFriends(): Flow<SearchModel> {
        return flow {
            val users = userApi.getFriends()
            emit(users)
        }
    }

    override suspend fun createPChat(userId: String): Flow<ResponseModel> {
        return flow {
            val users = userApi.createPChat(userId)
            emit(users)
        }
    }

    override suspend fun friendRequest(id: String): Flow<ResponseModel> {
        return flow {
            val users = userApi.friendRequest(id)
            emit(users)
        }
    }

    override suspend fun acceptRequest(id: String): Flow<ResponseModel> {
        return flow {
            val users = userApi.acceptReq(id)
            emit(users)
        }
    }

    override suspend fun fetchNotification(): Flow<ReceivedReqModel> {
        return flow {
            val users = userApi.fetchNotifications()
            emit(users)
        }
    }
}

