package com.example.cse5236

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cse5236.ui.DifficultyActivity
import com.example.cse5236.ui.GameActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(DifficultyActivity::class.java)
    @Before
    fun setUp() {
        Intents.init()  // Initialize Espresso Intents
    }
    @After
    fun tearDown() {
        Intents.release()  // Release intents here
    }
    @Test
    fun testEasyButtonStartsGame() {
        // Click "Easy" button
        onView(withId(R.id.btn_easy)).perform(click())

        // Check if intent was launched with correct extra
        intended(allOf(
            hasComponent(GameActivity::class.java.name),
            hasExtra("difficulty", "easy")
        ))
    }
    @Test
    fun testMediumButtonIntent() {
        onView(withId(R.id.btn_medium)).perform(click())

        intended(allOf(
            hasComponent(GameActivity::class.java.name),
            hasExtra("difficulty", "medium")
        ))
    }

    @Test
    fun testHardButtonIntent() {
        onView(withId(R.id.btn_hard)).perform(click())

        intended(allOf(
            hasComponent(GameActivity::class.java.name),
            hasExtra("difficulty", "hard")
        ))
    }
}