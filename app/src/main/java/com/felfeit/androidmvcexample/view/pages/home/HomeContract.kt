package com.felfeit.androidmvcexample.view.pages.home

import com.felfeit.androidmvcexample.model.post.response.Post
import com.felfeit.androidmvcexample.view.base.BaseView

interface HomeContract {
    interface View : BaseView{
        fun showPosts(posts: List<Post>)
    }

    interface Controller {
        fun getAllPosts()
        fun logout()
    }
}
