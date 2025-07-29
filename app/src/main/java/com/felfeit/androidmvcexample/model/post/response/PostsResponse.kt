package com.felfeit.androidmvcexample.model.post.response


import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("posts")
    val posts: List<Post>,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)

data class Reactions(
    @SerializedName("dislikes")
    val dislikes: Int,
    @SerializedName("likes")
    val likes: Int
)

data class Post(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("reactions")
    val reactions: Reactions,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("views")
    val views: Int
)
