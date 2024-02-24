package com.example.x_chat.domain.model.api.rec

import com.example.x_chat.domain.model.api.rec.sub.GroupModel
import com.example.x_chat.domain.model.api.rec.sub.OwnerModel

data class GroupInfoModel(
    val __v: Int,
    val group: GroupModel,
    val owners: List<OwnerModel>
)