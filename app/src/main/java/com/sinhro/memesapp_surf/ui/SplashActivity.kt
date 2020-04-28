package com.sinhro.memesapp_surf.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        openLoginScreenAfterDelay(300)
    }

    private fun openLoginScreenAfterDelay( millis:Long){
        Handler().postDelayed({
            openLoginScreen()
        },millis)
    }

    private fun openLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}