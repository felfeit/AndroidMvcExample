package com.felfeit.androidmvcexample.controller.auth

import com.felfeit.androidmvcexample.model.auth.repository.AuthRepository
import com.felfeit.androidmvcexample.model.auth.request.RegisterUserRequest
import com.felfeit.androidmvcexample.util.Resource
import com.felfeit.androidmvcexample.view.base.BaseController
import com.felfeit.androidmvcexample.view.pages.register.RegisterContract
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegisterController(
    private var view: RegisterContract.View?,
    private val authRepository: AuthRepository,
) : BaseController(), RegisterContract.Controller {

    override fun register(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            view?.showError("Semua field wajib untuk diisi")
            return
        }

        scope.launch {
            view?.showLoading()
            when (val result =
                authRepository.registerUser(RegisterUserRequest(username, email, password))) {
                is Resource.Error -> {
                    view?.hideLoading()
                    view?.showError(result.message ?: "Terjadi kesalahan tidak diketahui")
                }

                is Resource.Success -> {
                    view?.hideLoading()
                    view?.showError("Registrasi berhasil! Silakan login.")
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