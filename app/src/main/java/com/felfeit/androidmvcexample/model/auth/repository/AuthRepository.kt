package com.felfeit.androidmvcexample.model.auth.repository

import android.util.Log
import com.felfeit.androidmvcexample.model.auth.request.LoginUserRequest
import com.felfeit.androidmvcexample.model.auth.request.RefreshTokenRequest
import com.felfeit.androidmvcexample.model.auth.response.RefreshTokenResponse
import com.felfeit.androidmvcexample.model.auth.response.UserLoginResponse
import com.felfeit.androidmvcexample.network.ApiService
import com.felfeit.androidmvcexample.util.Resource

class AuthRepository(private val apiService: ApiService) {

    suspend fun loginUser(request: LoginUserRequest): Resource<UserLoginResponse> {
        return try {
            val response = apiService.loginUser(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Login Gagal: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message ?: "Unknown Message")
            Resource.Error(e.message ?: "Terjadi kesalahan tidak diketahui")
        }
    }

    suspend fun refreshToken(request: RefreshTokenRequest): Resource<RefreshTokenResponse> {
        return try {
            val response = apiService.refreshAuthSession(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message() ?: "Gagal mendapatkan refresh token")
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message ?: "Unknown Message")
            Resource.Error(e.message ?: "Terjadi kesalahan yang tidak diketahui")
        }
    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}