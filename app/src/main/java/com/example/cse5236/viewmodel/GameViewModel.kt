package com.example.cse5236.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cse5236.ui.GameActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.graphics.Color
import com.example.cse5236.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class GameViewModel : ViewModel() {
    fun updateScore(gameActivity: GameActivity) {
        CoroutineScope(Dispatchers.Main).launch {
            gameActivity.flashColor(Color.GREEN)
            gameActivity.playSound(R.raw.ding)
            delay(100L)
            gameActivity.score++
            gameActivity.showNextQuote()
            gameActivity.scoreUpdating = false
        }
    }

    suspend fun scoreDelay(gameActivity : GameActivity) {
        gameActivity.showNextQuote();
        delay(100L);
        gameActivity.score++;
        gameActivity.scoreUpdating = false;
    }

    fun skip(gameActivity: GameActivity) {
        CoroutineScope(Dispatchers.Main).launch {
            gameActivity.flashColor(Color.RED)
            gameActivity.playSound(R.raw.buzzer)
            delay(100L)
            gameActivity.showNextQuote()
        }
    }

    suspend fun skipDelay(gameActivity : GameActivity) {
        gameActivity.showNextQuote();
        delay(100L);
    }
}