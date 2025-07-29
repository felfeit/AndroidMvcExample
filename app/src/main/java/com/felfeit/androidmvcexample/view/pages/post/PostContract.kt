package com.felfeit.androidmvcexample.view.pages.post

import com.felfeit.androidmvcexample.model.post.response.Comment
import com.felfeit.androidmvcexample.model.post.response.Post
import com.felfeit.androidmvcexample.view.base.BaseView

interface PostContract {

    interface View: BaseView {
        fun showPost(post: Post)
        fun showPostComments(comments: List<Comment>)
    }

    interface Controller {
        fun getPost(id: Int)
        fun getPostComments(id: Int)
    }
}