package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val finalScore = intent.getIntExtra("score", 0)
        val highScore = intent.getIntExtra("highScore", 0)

        val scoreText: TextView = findViewById(R.id.scoreText)
        val highScoreText: TextView = findViewById(R.id.highScoreText)
        val playAgainBtn: Button = findViewById(R.id.btn_play_again)
        val homeBtn: Button = findViewById(R.id.btn_home)

        scoreText.text = "Your Score: $finalScore"
        highScoreText.text = "High Score: $highScore"

        playAgainBtn.setOnClickListener {
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
            finish()
        }

        homeBtn.setOnClickListener {
            val intent = Intent(this, UserSelectActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
