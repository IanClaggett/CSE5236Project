package com.example.cse5236

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth.signOut()

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
