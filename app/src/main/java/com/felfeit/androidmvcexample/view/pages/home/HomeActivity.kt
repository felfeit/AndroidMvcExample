package com.felfeit.androidmvcexample.view.pages.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.color.MaterialColors
import androidx.recyclerview.widget.LinearLayoutManager
import com.felfeit.androidmvcexample.R
import com.felfeit.androidmvcexample.controller.home.HomeController
import com.felfeit.androidmvcexample.databinding.ActivityHomeBinding
import com.felfeit.androidmvcexample.model.post.repository.PostRepository
import com.felfeit.androidmvcexample.model.post.response.Post
import com.felfeit.androidmvcexample.network.ApiConfig
import com.felfeit.androidmvcexample.util.DialogUtils
import com.felfeit.androidmvcexample.util.PreferenceManager
import com.felfeit.androidmvcexample.view.adapter.PostAdapter
import com.felfeit.androidmvcexample.view.pages.login.LoginActivity
import com.felfeit.androidmvcexample.view.pages.post.PostActivity
import com.google.android.material.R.attr
import dev.androidbroadcast.vbpd.viewBinding

class HomeActivity : AppCompatActivity(R.layout.activity_home), HomeContract.View {
    private val binding: ActivityHomeBinding by viewBinding()
    private lateinit var controller: HomeController
    private lateinit var postAdapter: PostAdapter
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = MaterialColors.getColor(this, attr.colorSurfaceContainerHigh, Color.BLACK)

        val repository = PostRepository(ApiConfig.getInstance(this))
        val prefs = PreferenceManager(this)
        controller = HomeController(this, repository, prefs)

        setSupportActionBar(binding.toolbar)
        setupToolbar()
        setupRecyclerView()

        controller.getAllPosts()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah kamu yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    controller.logout()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter().apply {
            setOnItemClickListener { postId ->
                startActivity(Intent(this@HomeActivity, PostActivity::class.java).apply {
                    putExtra(PostActivity.POST_ID_KEY, postId)
                })
            }
        }
        binding.rvPosts.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    override fun showLoading() {
        loadingDialog = DialogUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        loadingDialog?.takeIf { it.isShowing }?.dismiss()
    }

    override fun showError(message: String) {
        binding.tvMessage.apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    override fun showPosts(posts: List<Post>) {
        if (posts.isEmpty()) {
            binding.tvMessage.apply {
                text = "Belum ada postingan"
                visibility = View.VISIBLE
            }
            binding.rvPosts.visibility = View.GONE
        } else {
            binding.tvMessage.visibility = View.GONE
            binding.rvPosts.visibility = View.VISIBLE
            postAdapter.submitList(posts)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }
}