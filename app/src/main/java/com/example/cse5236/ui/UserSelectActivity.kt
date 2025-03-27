package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cse5236.R
import com.example.cse5236.model.User

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


    fun onUserSelect(user: User) {
        //TODO: send user to userHomeActivity
        startActivity(Intent(this, UserHomeActivity::class.java))
        finish()
    }

    fun onUserLogout() {
        startActivity(Intent(this, AuthenticationActivity::class.java))
        finish()
    }

    fun onUserSettings(){
        startActivity(Intent(this,AccountSettingsActivity::class.java))
        finish()
    }

}

