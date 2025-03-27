package com.example.cse5236.ui

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
        val quotesBtn = findViewById<Button>(R.id.btnQuotes)

        changeUserBtn.setOnClickListener{
            //back to UserSelectFragment
        }

        playBtn.setOnClickListener{
            //go to game
        }
        quotesBtn.setOnClickListener{
            //go to quotes screen
        }
    }

}