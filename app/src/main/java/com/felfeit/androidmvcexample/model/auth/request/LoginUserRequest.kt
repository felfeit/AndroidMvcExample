package com.felfeit.androidmvcexample.model.auth.request

import com.google.gson.annotations.SerializedName

data class LoginUserRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)