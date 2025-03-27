package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R

class DifficultyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        val easyBtn: Button = findViewById(R.id.btn_easy)
        val mediumBtn: Button = findViewById(R.id.btn_medium)
        val hardBtn: Button = findViewById(R.id.btn_hard)

        easyBtn.setOnClickListener { startGameWithDifficulty("easy") }
        mediumBtn.setOnClickListener { startGameWithDifficulty("medium") }
        hardBtn.setOnClickListener { startGameWithDifficulty("hard") }
    }

    private fun startGameWithDifficulty(difficulty: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
    }
}
