package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cse5236.R
import com.example.cse5236.viewmodel.UserViewModel

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val returnBtn = findViewById<Button>(R.id.return_button)

        returnBtn.setOnClickListener{
            startActivity(Intent(this,UserSelectActivity::class.java))
            finish()
        }
    }
}