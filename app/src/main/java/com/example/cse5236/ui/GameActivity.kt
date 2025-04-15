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
import com.example.cse5236.viewmodel.GameViewModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import kotlin.math.sqrt
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.View

class GameActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelValues = FloatArray(3)
    private var lastAccel = FloatArray(3)
    private var shakeThreshold = 12f
    private var quoteList: List<Quote> = emptyList()
    private var currentIndex = 0
    var score = 0
    private var highScore = 0
    private var characterHintShown = false
    var scoreUpdating = false
    private var gameEnded = false

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

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        loadQuotes()
        loadHighScore()

        if (savedInstanceState != null) {
            timeLeftInMillis = savedInstanceState.getLong("timeLeftInMillis", 60000)
            score = savedInstanceState.getInt("score", 0)
            currentIndex = savedInstanceState.getInt("currentIndex", 0)
            showQuoteByIndex(currentIndex)
        } else {
            val difficulty = intent.getStringExtra("difficulty")
            timeLeftInMillis = when (difficulty) {
                "easy" -> 120000
                "medium" -> 60000
                "hard" -> 30000
                else -> 60000
            }
            showNextQuote()
        }

        startTimer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("timeLeftInMillis", timeLeftInMillis)
        outState.putInt("score", score)
        outState.putInt("currentIndex", currentIndex)
    }

    fun showQuoteByIndex(index: Int) {
        if (quoteList.isNotEmpty()) {
            val quote = quoteList[index]
            quoteText.text = "\"${quote.quote}\""
            characterHintShown = false
        }
    }

    fun flashColor(color: Int) {
        val rootView = findViewById<View>(android.R.id.content)
        val original = rootView.background
        rootView.setBackgroundColor(color)

        Handler(Looper.getMainLooper()).postDelayed({
            rootView.background = original
        }, 200)
    }

    fun playSound(soundResId: Int) {
        val prefs = getSharedPreferences("game", Context.MODE_PRIVATE)
        val audioEnabled = prefs.getBoolean("audioEnabled", true)

        if (!audioEnabled) return

        val mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.setOnCompletionListener { it.release() }
        mediaPlayer.start()
    }

    private fun loadQuotes() {
        val inputStream: InputStream = assets.open("quotes.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Quote>>() {}.type
        quoteList = Gson().fromJson(json, type)
    }

    fun showNextQuote() {
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
        if (gameEnded) return
        gameEnded = true

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
        if (::timer.isInitialized) {
            timer.cancel()
        }
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
                hintLabel.text = ""
                GameViewModel().skip(this)
            }

            // Tilt Up (Correct)
            if (z > 7 && !scoreUpdating && z < 7.8) {
                scoreUpdating = true;
                hintLabel.text = ""
                GameViewModel().updateScore(this)
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
