package com.example.cse5236

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.ui.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth

class UserSelectActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select)

        auth = FirebaseAuth.getInstance()

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val accountSettings = findViewById<Button>(R.id.accountSettings)

        btnLogout.setOnClickListener {
            auth.signOut()  // Logs the user out
            val intent = Intent(this, AuthenticationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Clears activity stack
            startActivity(intent)
        }

        accountSettings.setOnClickListener{
            val intent = Intent(this, AccountSettingsActivity:: class.java)
            startActivity(intent)
        }
    }

}

