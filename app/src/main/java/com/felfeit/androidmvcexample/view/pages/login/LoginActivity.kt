package com.felfeit.androidmvcexample.view.pages.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.felfeit.androidmvcexample.R
import com.felfeit.androidmvcexample.controller.auth.LoginController
import com.felfeit.androidmvcexample.databinding.ActivityLoginBinding
import com.felfeit.androidmvcexample.model.auth.repository.AuthRepository
import com.felfeit.androidmvcexample.network.ApiConfig
import com.felfeit.androidmvcexample.util.DialogUtils
import com.felfeit.androidmvcexample.util.PreferenceManager
import com.felfeit.androidmvcexample.view.pages.home.HomeActivity
import com.felfeit.androidmvcexample.view.pages.register.RegisterActivity
import dev.androidbroadcast.vbpd.viewBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login), LoginContract.View {
    private val binding: ActivityLoginBinding by viewBinding()
    private lateinit var controller: LoginController
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = AuthRepository(ApiConfig.getInstance(this))
        val prefs = PreferenceManager(this)
        controller = LoginController(this, repository, prefs)

        binding.loginButton.setOnClickListener {
            val username = binding.textInputUsername.text.toString().trim()
            val password = binding.textInputPassword.text.toString().trim()
            controller.login(username, password)
        }

        switchToRegister()
    }

    override fun showLoading() {
        loadingDialog = DialogUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        loadingDialog?.takeIf { it.isShowing }?.dismiss()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun switchToRegister() {
        binding.navigateToRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }
}