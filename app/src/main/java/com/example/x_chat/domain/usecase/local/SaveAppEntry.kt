package com.example.x_chat.domain.usecase.local

import com.example.x_chat.domain.manager.LocalUserManager

class SaveAppEntry (
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(token: String) {
        localUserManager.saveAppEntry(token = token)
    }
}