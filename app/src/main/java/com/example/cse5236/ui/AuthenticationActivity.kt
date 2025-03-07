package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cse5236.R
import com.example.cse5236.UserSelectActivity

class AuthenticationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showWelcomeScreen()
    }

    private fun showWelcomeScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameFragmentView, WelcomeFragment())
            .commit()
    }

    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameFragmentView, fragment)
            .commit()
    }

    fun onUserSignedIn() {
        navigateToHomeScreen()
    }

    private fun navigateToHomeScreen() {
        startActivity(Intent(this, UserSelectActivity::class.java))
        finish()
    }
}
