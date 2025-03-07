package com.example.cse5236.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cse5236.R

class UserHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserHomeFragment.newInstance())
                .commitNow()
        }
    }
}