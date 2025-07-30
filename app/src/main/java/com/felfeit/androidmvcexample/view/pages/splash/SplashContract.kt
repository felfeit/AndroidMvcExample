package com.felfeit.androidmvcexample.view.pages.splash

interface SplashContract {
    interface View {
        fun navigateToLogin()
        fun navigateToHome()
    }

    interface Controller {
        fun checkSession()
    }
}