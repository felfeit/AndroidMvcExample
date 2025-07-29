package com.felfeit.androidmvcexample.model.auth.request

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)