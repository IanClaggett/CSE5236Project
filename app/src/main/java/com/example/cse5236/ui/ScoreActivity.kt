package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

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


        val user = Firebase.auth.currentUser

        user?.let {
            val fireStore = Firebase.firestore
            val docRef = fireStore.collection("UserInformationDB").document(it.email.toString())
            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    lateinit var userData: Map<Any, Any>
                    userData = document.data as Map<Any, Any>
                    val score = Integer.parseInt(userData.get("Score").toString())
                    val username = userData.get("Username")
                    if(score < finalScore){
                        val updateMap = mutableMapOf("Username" to username.toString(), "Score" to finalScore)
                        fireStore.collection("UserInformationDB").document(it.email.toString()).set(updateMap).addOnSuccessListener {
                            System.out.println("SUCCESS")
                        }.addOnFailureListener{
                            System.out.println("FAILURE")
                        }
                    }
                }
            }
        }
        scoreText.text = "Your Score: $finalScore"
        highScoreText.text = "High Score: $highScore"

        playAgainBtn.setOnClickListener {
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
            finish()
        }
//TODO: CHANGE TO USERHOME
        homeBtn.setOnClickListener {
            val intent = Intent(this, UserSelectActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
