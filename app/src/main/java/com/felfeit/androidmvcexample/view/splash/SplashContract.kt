package com.felfeit.androidmvcexample.view.splash

interface SplashContract {
    interface View {
        fun navigateToLogin()
        fun navigateToHome()
    }

    interface Controller {
        fun checkSession()
    }
}