package com.felfeit.androidmvcexample.controller.post

import com.felfeit.androidmvcexample.model.post.repository.PostRepository
import com.felfeit.androidmvcexample.util.Resource
import com.felfeit.androidmvcexample.view.base.BaseController
import com.felfeit.androidmvcexample.view.pages.post.PostContract
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PostController(
    private var view: PostContract.View?,
    private val postRepository: PostRepository,
) : BaseController(), PostContract.Controller {
    override fun getPost(id: Int) {
        scope.launch {
            view?.showLoading()
            when (val result = postRepository.getSinglePost(id)) {
                is Resource.Error -> {
                    view?.hideLoading()
                    view?.showError(result.message ?: "Terjadi kesalahan tidak diketahui")
                }
                is Resource.Success -> {
                    view?.hideLoading()
                    view?.showPost(result.data!!)
                }
            }
        }
    }

    override fun getPostComments(id: Int) {
        scope.launch {
            when (val result = postRepository.getPostComments(id)) {
                is Resource.Error -> {
                    view?.hideLoading()
                    view?.showError(result.message ?: "Terjadi kesalahan tidak diketahui")
                }
                is Resource.Success -> {
                    view?.hideLoading()
                    view?.showPostComments(result.data ?: emptyList())
                }
            }
        }
    }

    override fun onDestroy() {
        view = null
        scope.cancel()
    }
}