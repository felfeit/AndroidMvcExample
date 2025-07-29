package com.felfeit.androidmvcexample.view.pages.register

import com.felfeit.androidmvcexample.view.base.BaseView

interface RegisterContract {

    interface View : BaseView {
        fun navigateToLogin()
    }

    interface Controller {
        fun register(username: String, email: String, password: String)
    }
}