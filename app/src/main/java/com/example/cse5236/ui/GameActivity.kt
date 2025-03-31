package com.example.cse5236.ui

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import kotlin.math.sqrt

class GameActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelValues = FloatArray(3)
    private var lastAccel = FloatArray(3)
    private var shakeThreshold = 12f
    private var quoteList: List<Quote> = emptyList()
    private var currentIndex = 0
    private var score = 0
    private var highScore = 0
    private var characterHintShown = false
    private var scoreUpdating = false

    private lateinit var quoteText: TextView
    private lateinit var hintLabel: TextView
    private lateinit var timerText: TextView

    private var timeLeftInMillis: Long = 60000 // Default: 60 seconds
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        quoteText = findViewById(R.id.quoteText)
        hintLabel = findViewById(R.id.hintLabel)
        timerText = findViewById(R.id.timerText)

        val difficulty = intent.getStringExtra("difficulty")
        timeLeftInMillis = when (difficulty) {
            "easy" -> 120000
            "medium" -> 60000
            "hard" -> 30000
            else -> 60000
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        loadQuotes()
        loadHighScore()

        // ✅ Only proceed if quotes were successfully loaded
        if (quoteList.isNotEmpty()) {
            showNextQuote()
            startTimer()
        } else {
            Toast.makeText(this, "Could not load quotes.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadQuotes() {
        val inputStream: InputStream = assets.open("quotes.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Quote>>() {}.type
        quoteList = Gson().fromJson(json, type)
    }

    private fun showNextQuote() {
        if (quoteList.isNullOrEmpty()) {
            Toast.makeText(this, "No quotes found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        currentIndex = (quoteList.indices).random()
        val quote = quoteList[currentIndex]
        quoteText.text = "\"${quote.quote}\""
        characterHintShown = false
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                timerText.text = "Time: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
    }

    private fun endGame() {
        saveHighScore()
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("highScore", highScore)
        startActivity(intent)
        finish()
    }

    private fun saveHighScore() {
        val prefs = getSharedPreferences("game", Context.MODE_PRIVATE)
        if (score > highScore) {
            highScore = score
            prefs.edit().putInt("highScore", highScore).apply()
        }
    }

    private fun loadHighScore() {
        val prefs = getSharedPreferences("game", Context.MODE_PRIVATE)
        highScore = prefs.getInt("highScore", 0)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            accelValues = floatArrayOf(x, y, z)
            val deltaX = accelValues[0] - lastAccel[0]
            val deltaY = accelValues[1] - lastAccel[1]
            val deltaZ = accelValues[2] - lastAccel[2]

            val shake = sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble()).toFloat()
            lastAccel = accelValues

            if (shake > shakeThreshold && !characterHintShown) {
                characterHintShown = true
                hintLabel.text = "Hint: ${quoteList[currentIndex].character}"
            }

            // Tilt Down (Skip)
            if (z < -5 ) {
                Thread.sleep(100)
                hintLabel.text = ""
                showNextQuote()
            }

            // Tilt Up (Correct)
            if (z > 7 && !scoreUpdating && z < 7.8) {
                scoreUpdating = true;
                Thread.sleep(100)
                score++
                hintLabel.text = ""
                showNextQuote()
                scoreUpdating = false;
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    data class Quote(
        @SerializedName("Quote") val quote: String,
        @SerializedName("Anime") val from: String,
        @SerializedName("Character") val character: String
    )
}
