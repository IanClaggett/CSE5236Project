package com.example.cse5236.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R

class AccountAddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val addUserBtn = findViewById<Button>(R.id.addUserButton)

        addUserBtn.setOnClickListener{
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val prefs = getSharedPreferences("users", Context.MODE_PRIVATE)
            prefs.edit().putString(email,password).apply()
            Toast.makeText(this, "Saved User login settings", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,UserSelectActivity::class.java))
            finish()
        }
    }
}