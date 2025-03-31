package com.example.cse5236.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R
import com.example.cse5236.viewmodel.UserViewModel

class AccountAddActivity: AppCompatActivity() {

    private var viewModel: UserViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val addUserBtn = findViewById<Button>(R.id.addUserButton)

        addUserBtn.setOnClickListener{
            val username = usernameInput.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.addUser(username)
            val prefs = getSharedPreferences("users", Context.MODE_PRIVATE)
            prefs.edit().putString(username,username).apply()
            Toast.makeText(this, "Saved User", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UserSelectActivity::class.java))
            finish()
        }
    }
}