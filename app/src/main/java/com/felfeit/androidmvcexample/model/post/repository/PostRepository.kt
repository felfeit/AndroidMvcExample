package com.felfeit.androidmvcexample.model.post.repository

import com.felfeit.androidmvcexample.model.post.response.Comment
import com.felfeit.androidmvcexample.model.post.response.Post
import com.felfeit.androidmvcexample.network.ApiService
import com.felfeit.androidmvcexample.util.Resource

class PostRepository(private val apiService: ApiService) {

    suspend fun getAllPosts() : Resource<List<Post>> {
        return try {
            val response = apiService.getAllPosts()
            if(response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()?.posts ?: emptyList())
            } else {
                Resource.Error("Gagal mendapatkan postingan: ${response.message()}")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan yang tidak diketahui")
        }
    }

    suspend fun getSinglePost(id: Int): Resource<Post> {
        return try {
            val response = apiService.getSinglePost(id)
            if(response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Gagal mendapatkan postingan: ${response.message()}")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan yang tidak diketahui")
        }
    }

    suspend fun getPostComments(id: Int): Resource<List<Comment>> {
        return try {
            val response = apiService.getPostComments(id)
            if(response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()?.comments ?: emptyList())
            } else {
                Resource.Error("Gagal mendapatkan komentar postingan: ${response.message()}")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan yang tidak diketahui")
        }
    }
}