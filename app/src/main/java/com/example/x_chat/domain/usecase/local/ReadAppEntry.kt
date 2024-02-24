package com.example.x_chat.domain.usecase.local

import com.example.x_chat.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (
    private val localUserManager: LocalUserManager
){
    operator fun invoke(): Flow<String> {
        return localUserManager.readAppEntry()
    }
}