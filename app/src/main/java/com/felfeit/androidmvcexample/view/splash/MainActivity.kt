package com.felfeit.androidmvcexample.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.felfeit.androidmvcexample.R
import com.felfeit.androidmvcexample.controller.splash.SplashController
import com.felfeit.androidmvcexample.model.auth.repository.AuthRepository
import com.felfeit.androidmvcexample.network.ApiConfig
import com.felfeit.androidmvcexample.util.PreferenceManager
import com.felfeit.androidmvcexample.view.pages.home.HomeActivity
import com.felfeit.androidmvcexample.view.pages.login.LoginActivity

class MainActivity : AppCompatActivity(R.layout.activity_main), SplashContract.View {
    private lateinit var controller: SplashController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = AuthRepository(ApiConfig.getInstance(this))
        val prefs = PreferenceManager(this)

        controller = SplashController(this, repository, prefs)
        controller.checkSession()
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }
}