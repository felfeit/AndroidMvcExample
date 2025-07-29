package com.felfeit.androidmvcexample.model.post.response


import com.google.gson.annotations.SerializedName

data class PostCommentsResponse(
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)

data class Comment(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("user")
    val user: User
)


data class User(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String
)