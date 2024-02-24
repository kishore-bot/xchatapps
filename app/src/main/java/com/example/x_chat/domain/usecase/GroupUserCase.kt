package com.example.x_chat.domain.usecase

import com.example.x_chat.domain.usecase.group.AddMembers
import com.example.x_chat.domain.usecase.group.CreateGroup
import com.example.x_chat.domain.usecase.group.FetchGChatInfo
import com.example.x_chat.domain.usecase.group.LeaveGroup
import com.example.x_chat.domain.usecase.group.RemoveUser
import com.example.x_chat.domain.usecase.group.UploadGroupAbout
import com.example.x_chat.domain.usecase.group.UploadGroupName

data class GroupUserCase(
    val fetchGChatInfo: FetchGChatInfo,
    val uploadGroupName: UploadGroupName,
    val uploadGroupAbout: UploadGroupAbout,
    val leaveGroup: LeaveGroup,
    val removeUser: RemoveUser,
    val createGroup: CreateGroup,
    val addMembers: AddMembers,
)