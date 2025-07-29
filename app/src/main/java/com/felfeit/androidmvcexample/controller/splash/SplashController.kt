package com.felfeit.androidmvcexample.controller.splash

import com.felfeit.androidmvcexample.model.auth.repository.AuthRepository
import com.felfeit.androidmvcexample.model.auth.request.RefreshTokenRequest
import com.felfeit.androidmvcexample.util.PreferenceManager
import com.felfeit.androidmvcexample.util.Resource
import com.felfeit.androidmvcexample.view.base.BaseController
import com.felfeit.androidmvcexample.view.splash.SplashContract
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashController(
    private var view: SplashContract.View?,
    private val authRepository: AuthRepository,
    private val prefs: PreferenceManager,
) : BaseController(), SplashContract.Controller {
    override fun checkSession() {
        scope.launch {
            delay(2000L)

            if (!prefs.hasToken() || prefs.isTokenExpired()) {
                prefs.clearToken()
                view?.navigateToLogin()
                return@launch
            }

            val refreshToken = prefs.getRefreshToken()
            val result = authRepository.refreshToken(RefreshTokenRequest(refreshToken!!))

            when (result) {
                is Resource.Success -> {
                    val newAccessToken = result.data!!.accessToken
                    val newRefreshToken = result.data.refreshToken
                    prefs.saveTokens(newAccessToken, newRefreshToken)
                    view?.navigateToHome()
                }

                else -> {
                    prefs.clearToken()
                    view?.navigateToLogin()
                }
            }
        }
    }

    override fun onDestroy() {
        view = null
        scope.cancel()
    }
}