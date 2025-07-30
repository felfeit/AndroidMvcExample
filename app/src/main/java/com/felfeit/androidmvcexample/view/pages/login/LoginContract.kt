package com.felfeit.androidmvcexample.view.pages.login

import com.felfeit.androidmvcexample.view.base.BaseView

interface LoginContract {

    interface View : BaseView {
        fun navigateToHome()
    }

    interface Controller {
        fun login(username: String, password: String)
    }
}