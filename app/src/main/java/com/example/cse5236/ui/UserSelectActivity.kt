package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cse5236.R

class UserSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select)
        showSelectScreen()
    }

    private fun showSelectScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameFragmentView, UserSelectFragment())
            .commit()
    }

    //need for switching with UserAdd and UserSelect frag
    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameFragmentView, fragment)
            .commit()
    }

    fun onUserSelect() {
        navigateToHomeScreen()
    }

    fun onUserLogout() {
        startActivity(Intent(this, AuthenticationActivity::class.java))
        finish()
    }

    private fun navigateToHomeScreen() {
        startActivity(Intent(this, UserHomeActivity::class.java))
        finish()
    }

}

