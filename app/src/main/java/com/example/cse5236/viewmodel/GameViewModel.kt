package com.example.cse5236.viewmodel

import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.cse5236.ui.GameActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GameViewModel : ViewModel() {
    fun updateScore(gameActivity: GameActivity){
        runBlocking {
            launch { scoreDelay(gameActivity) }
        }
    }

    suspend fun scoreDelay(gameActivity : GameActivity) {
        delay(100L);
        gameActivity.score++;
        gameActivity.showNextQuote();
        gameActivity.scoreUpdating = false;
    }

    fun skip(gameActivity: GameActivity){
        runBlocking {
            launch { skipDelay(gameActivity) }
        }
    }

    suspend fun skipDelay(gameActivity : GameActivity) {
        delay(100L);
        gameActivity.showNextQuote();
    }
}