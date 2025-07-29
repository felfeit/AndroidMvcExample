package com.felfeit.androidmvcexample.controller.auth

import com.felfeit.androidmvcexample.model.auth.repository.AuthRepository
import com.felfeit.androidmvcexample.model.auth.request.LoginUserRequest
import com.felfeit.androidmvcexample.util.PreferenceManager
import com.felfeit.androidmvcexample.util.Resource
import com.felfeit.androidmvcexample.view.base.BaseController
import com.felfeit.androidmvcexample.view.pages.login.LoginContract
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginController(
    private var view: LoginContract.View?,
    private val authRepository: AuthRepository,
    private val prefsManager: PreferenceManager,
) : BaseController(), LoginContract.Controller {

    override fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            view?.showError("Semua field wajib diisi")
            return
        }

        scope.launch {
            view?.showLoading()
            when (val result = authRepository.loginUser(LoginUserRequest(username, password))) {
                is Resource.Error -> {
                    view?.hideLoading()
                    view?.showError(result.message ?: "Terjadi kesalahan tidak diketahui")
                }
                is Resource.Success -> {
                    view?.hideLoading()
                    val data = result.data!!
                    val accessToken = data.accessToken
                    val refreshToken = data.refreshToken
                    prefsManager.saveTokens(accessToken, refreshToken)
                    view?.navigateToHome()
                }
            }
        }
    }

    override fun onDestroy() {
        view = null
        scope.cancel()
    }
}