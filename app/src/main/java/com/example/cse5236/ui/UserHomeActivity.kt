package com.example.cse5236.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cse5236.R

class UserHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        val changeUserBtn = findViewById<Button>(R.id.btnChangeUser)
        val playBtn = findViewById<Button>(R.id.btnPlay)

        changeUserBtn.setOnClickListener{
            val intent = Intent(this, UserSelectActivity::class.java)
            startActivity(intent)
            finish()
        }

        playBtn.setOnClickListener{
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}