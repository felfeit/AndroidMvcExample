package com.felfeit.androidmvcexample.view.pages.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.felfeit.androidmvcexample.R
import com.felfeit.androidmvcexample.controller.auth.RegisterController
import com.felfeit.androidmvcexample.databinding.ActivityRegisterBinding
import com.felfeit.androidmvcexample.model.auth.repository.AuthRepository
import com.felfeit.androidmvcexample.network.ApiConfig
import com.felfeit.androidmvcexample.util.DialogUtils
import com.felfeit.androidmvcexample.view.pages.login.LoginActivity
import dev.androidbroadcast.vbpd.viewBinding

class RegisterActivity : AppCompatActivity(R.layout.activity_register), RegisterContract.View {
    private val binding: ActivityRegisterBinding by viewBinding()
    private lateinit var controller: RegisterController
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = AuthRepository(ApiConfig.getInstance(this))
        controller = RegisterController(this, repository)

        binding.registerButton.setOnClickListener {
            val username = binding.textInputUsername.text.toString().trim()
            val email = binding.textInputEmail.text.toString().trim()
            val password = binding.textInputPassword.text.toString().trim()
            controller.register(username, email, password)
        }

        switchToLogin()
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

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun switchToLogin() {
        binding.navigateToLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }
}