package com.felfeit.androidmvcexample.model.auth.request

data class RefreshTokenRequest(
    val refreshToken: String,
    val expiresInMins: Int = 60,
)