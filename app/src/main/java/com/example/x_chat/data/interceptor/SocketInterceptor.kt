package com.example.x_chat.data.interceptor

import android.util.Log
import com.example.x_chat.domain.manager.LocalUserManager
import io.socket.client.IO
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SocketInterceptor @Inject constructor(
    private val localUserManager: LocalUserManager,
)  {
    fun createSocketIOOptions(): IO.Options = runBlocking {

        val authToken = localUserManager.readAppEntry().firstOrNull()
        val options = IO.Options()
        options.extraHeaders = mapOf("Authorization" to listOf("Bearer $authToken"))
        options
    }
}