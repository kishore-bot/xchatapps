package com.example.x_chat.domain.model.api.rec

import com.example.x_chat.domain.model.api.rec.sub.SearchUserModel

data class SearchModel(
    val users: List<SearchUserModel>
)