package com.example.x_chat.domain.model.api.rec

import com.example.x_chat.domain.model.api.rec.sub.RequestsModel

data class ReceivedReqModel(
    val requests: List<RequestsModel>
)