package com.felfeit.androidmvcexample.network

import com.felfeit.androidmvcexample.util.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val prefs: PreferenceManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        // Skip adding token for login
        val isAuthExcluded = path.contains("/auth/login")

        return if (isAuthExcluded) {
            chain.proceed(request)
        } else {
            val token = prefs.getAccessToken()
            val requestWithToken = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestWithToken)
        }
    }
}