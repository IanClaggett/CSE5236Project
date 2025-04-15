package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val returnBtn = findViewById<Button>(R.id.return_button)
        val audioSwitch = findViewById<android.widget.Switch>(R.id.switch_audio)

        val prefs = getSharedPreferences("game", MODE_PRIVATE)
        val audioEnabled = prefs.getBoolean("audioEnabled", true)

        audioSwitch.isChecked = audioEnabled

        audioSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("audioEnabled", isChecked).apply()
        }

        returnBtn.setOnClickListener {
            startActivity(Intent(this, UserSelectActivity::class.java))
            finish()
        }
    }
}