package com.example.x_chat.domain.usecase

import com.example.x_chat.domain.usecase.local.DeleteAppEntry
import com.example.x_chat.domain.usecase.local.ReadAppEntry
import com.example.x_chat.domain.usecase.local.SaveAppEntry

data class AppEntryUseCases (
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
    val deleteAppEntry: DeleteAppEntry
)
