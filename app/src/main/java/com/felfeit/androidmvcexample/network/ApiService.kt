package com.felfeit.androidmvcexample.network

import com.felfeit.androidmvcexample.model.auth.request.LoginUserRequest
import com.felfeit.androidmvcexample.model.auth.request.RefreshTokenRequest
import com.felfeit.androidmvcexample.model.auth.response.RefreshTokenResponse
import com.felfeit.androidmvcexample.model.auth.response.UserLoginResponse
import com.felfeit.androidmvcexample.model.post.response.Post
import com.felfeit.androidmvcexample.model.post.response.PostCommentsResponse
import com.felfeit.androidmvcexample.model.post.response.PostsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun loginUser(@Body body: LoginUserRequest): Response<UserLoginResponse>

    @POST("auth/refresh")
    suspend fun refreshAuthSession(@Body body: RefreshTokenRequest): Response<RefreshTokenResponse>

    @GET("posts")
    suspend fun getAllPosts(): Response<PostsResponse>

    @GET("posts/{id}")
    suspend fun getSinglePost(@Path("id") id: Int): Response<Post>

    @GET("posts/{id}/comments")
    suspend fun getPostComments(@Path("id") id: Int): Response<PostCommentsResponse>
}