package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    fun onAddAccount(){
        startActivity(Intent(this, AccountAddActivity::class.java))
        finish()
    }

    /*fun onManageAccount(){
        startActivity(Intent(this, ManageUsersActivity::class.java))
        finish()
    }*/

    fun onSettings(){
        startActivity(Intent(this, SettingsActivity::class.java))
        finish()
    }

}

