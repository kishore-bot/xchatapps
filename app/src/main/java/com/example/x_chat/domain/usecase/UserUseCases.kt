package com.example.x_chat.domain.usecase

import com.example.x_chat.domain.usecase.user.AcceptRequest
import com.example.x_chat.domain.usecase.user.CreatePChat
import com.example.x_chat.domain.usecase.user.FetchMe
import com.example.x_chat.domain.usecase.user.FetchPChatInfo
import com.example.x_chat.domain.usecase.user.UploadAbout
import com.example.x_chat.domain.usecase.user.FetchAllFriends
import com.example.x_chat.domain.usecase.user.FetchNotification
import com.example.x_chat.domain.usecase.user.FriendRequest
import com.example.x_chat.domain.usecase.user.SearchUser

data class UserUseCases(
    val fetchMe: FetchMe,
    val uploadAbout: UploadAbout,
    val fetchPChatInfo: FetchPChatInfo,
    val searchUser: SearchUser,
    val fetchAllFriends: FetchAllFriends,
    val createPChat: CreatePChat,
    val acceptRequest: AcceptRequest,
    val friendRequest: FriendRequest,
    val fetchNotification: FetchNotification

    )
