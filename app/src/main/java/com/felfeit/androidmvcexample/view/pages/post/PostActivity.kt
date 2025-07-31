package com.felfeit.androidmvcexample.view.pages.post

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.felfeit.androidmvcexample.R
import com.felfeit.androidmvcexample.controller.post.PostController
import com.felfeit.androidmvcexample.databinding.ActivityPostBinding
import com.felfeit.androidmvcexample.model.post.repository.PostRepository
import com.felfeit.androidmvcexample.model.post.response.Comment
import com.felfeit.androidmvcexample.model.post.response.Post
import com.felfeit.androidmvcexample.network.ApiConfig
import com.felfeit.androidmvcexample.util.DialogUtils
import com.felfeit.androidmvcexample.view.adapter.CommentAdapter
import com.google.android.material.R.attr
import com.google.android.material.color.MaterialColors
import dev.androidbroadcast.vbpd.viewBinding

class PostActivity : AppCompatActivity(R.layout.activity_post), PostContract.View {
    private val binding: ActivityPostBinding by viewBinding()
    private lateinit var controller: PostController
    private var loadingDialog: AlertDialog? = null
    private lateinit var adapter: CommentAdapter
    private var isPostLoaded = false
    private var areCommentsLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor =
            MaterialColors.getColor(this, attr.colorSurfaceContainerHigh, Color.BLACK)
        val repository = PostRepository(ApiConfig.getInstance(this))
        controller = PostController(this, repository)

        val postId = intent.getIntExtra(POST_ID_KEY, -1)
        if (postId == -1) {
            Toast.makeText(this, "Post tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar()
        setupRecyclerView()
        controller.getPost(postId)
        controller.getPostComments(postId)
    }

    private fun setupToolbar() {
        val toolbar = binding.postToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = CommentAdapter()
        binding.rvComments.layoutManager = LinearLayoutManager(this)
        binding.rvComments.adapter = adapter
    }

    override fun showPost(post: Post) {
        binding.apply {
            tvPostTitle.text = post.title
            tvPostBody.text = post.body
            tvPostTags.text = "Tags: ${post.tags.joinToString(", ")}"
            textDislikes.text = "${post.reactions.dislikes} dislikes"
            textLikes.text = "${post.reactions.likes} likes"
            textViews.text = "${post.views} views"
        }

        isPostLoaded = true
        checkIfAllLoaded()
    }

    override fun showPostComments(comments: List<Comment>) {
        adapter.submitList(comments)
        areCommentsLoaded = true
        checkIfAllLoaded()
    }

    private fun checkIfAllLoaded() {
        if (isPostLoaded && areCommentsLoaded) {
            hideLoading()
        }
    }

    override fun showLoading() {
        loadingDialog = DialogUtils.showLoadingDialog(this)
        binding.contentContainer.visibility = View.GONE
    }

    override fun hideLoading() {
        loadingDialog?.takeIf { it.isShowing }?.dismiss()
        binding.contentContainer.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }

    companion object {
        const val POST_ID_KEY = "post_id"
    }
}