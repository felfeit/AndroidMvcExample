package com.felfeit.androidmvcexample.controller.home

import com.felfeit.androidmvcexample.model.post.repository.PostRepository
import com.felfeit.androidmvcexample.util.PreferenceManager
import com.felfeit.androidmvcexample.util.Resource
import com.felfeit.androidmvcexample.view.base.BaseController
import com.felfeit.androidmvcexample.view.pages.home.HomeContract
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HomeController(
    private var view: HomeContract.View?,
    private val postRepository: PostRepository,
    private val prefs: PreferenceManager,
) : BaseController(), HomeContract.Controller {

    override fun getAllPosts() {
        scope.launch {
            view?.showLoading()
            when (val result = postRepository.getAllPosts()) {
                is Resource.Error -> {
                    view?.hideLoading()
                    view?.showError(result.message ?: "Terjadi kesalahan tidak diketahui")
                }
                is Resource.Success -> {
                    view?.hideLoading()
                    view?.showPosts(result.data ?: emptyList())
                }
            }
        }
    }

    override fun logout() {
        prefs.clearToken()
    }

    override fun onDestroy() {
        view = null
        scope.cancel()
    }
}