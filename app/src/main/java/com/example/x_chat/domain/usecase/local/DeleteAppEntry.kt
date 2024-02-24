package com.example.x_chat.domain.usecase.local

import com.example.x_chat.domain.manager.LocalUserManager

class DeleteAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.deleteAppEntry()
    }
}