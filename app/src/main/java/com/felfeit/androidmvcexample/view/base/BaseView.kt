package com.felfeit.androidmvcexample.view.base

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(message: String)
}