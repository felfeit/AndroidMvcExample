package com.felfeit.androidmvcexample.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit().apply {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken)
            putLong(TOKEN_TIMESTAMP, System.currentTimeMillis())
            apply()
        }
    }

    fun isTokenExpired(): Boolean {
        val savedTime = prefs.getLong(TOKEN_TIMESTAMP, 0)
        val oneHour = 60 * 60 * 1000 // 1 jam
        return System.currentTimeMillis() - savedTime > oneHour
    }

    fun getRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN_KEY, null)
    }

    fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }

    fun hasToken(): Boolean {
        return getAccessToken() != null && getRefreshToken() != null
    }

    fun clearToken() {
        prefs.edit().apply {
            remove(ACCESS_TOKEN_KEY)
            remove(REFRESH_TOKEN_KEY)
            apply()
        }
    }

    companion object {
        private const val PREFS_NAME = "MyAppSession"
        private const val ACCESS_TOKEN_KEY = "access_token_key"
        private const val REFRESH_TOKEN_KEY = "refresh_token_key"
        private const val TOKEN_TIMESTAMP = "token_time_key"
    }
}