package com.example.x_chat.data.interceptor

import com.example.x_chat.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UserApiInterceptor @Inject constructor(
    private val localUserManager: LocalUserManager,
) : Interceptor {
    private var token: String = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        if (token == "") {
            token = runBlocking {
                localUserManager.readAppEntry().first()
            }
        }
        val request =
            chain.request().newBuilder().addHeader("Content-Type", "application/json").addHeader(
                "Authorization", "Bearer $token"
            )
                .build()
        return chain.proceed(request)
    }
}