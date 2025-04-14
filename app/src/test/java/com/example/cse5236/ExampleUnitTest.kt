package com.example.cse5236

import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.test.core.app.ApplicationProvider

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import com.example.cse5236.ui.AccountAddActivity
import com.example.cse5236.ui.DifficultyActivity
import com.example.cse5236.ui.UserHomeActivity
import com.example.cse5236.ui.UserSelectActivity
import org.robolectric.shadows.ShadowToast
import com.google.firebase.FirebaseApp
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        FirebaseApp.initializeApp(context)
    }

    @Test
    fun `empty username shows toast`() {
        val activity = Robolectric.buildActivity(AccountAddActivity::class.java).create().get()
        val usernameInput = activity.findViewById<EditText>(R.id.usernameInput)
        val addUserBtn = activity.findViewById<Button>(R.id.addUserButton)

        usernameInput.setText("")
        addUserBtn.performClick()

        val latestToastText = ShadowToast.getTextOfLatestToast()
        assertEquals("Please enter username", latestToastText)
    }

    @Test
    fun `change user button navigates to UserSelectActivity`() {
        val activity = Robolectric.buildActivity(UserHomeActivity::class.java).create().get()
        val button = activity.findViewById<Button>(R.id.btnChangeUser)
        button.performClick()

        val nextIntent = Shadows.shadowOf(activity).nextStartedActivity
        assertEquals(UserSelectActivity::class.java.name, nextIntent.component?.className)
    }

    @Test
    fun `play button navigates to DifficultyActivity`() {
        val activity = Robolectric.buildActivity(UserHomeActivity::class.java).create().get()
        val button = activity.findViewById<Button>(R.id.btnPlay)
        button.performClick()

        val nextIntent = Shadows.shadowOf(activity).nextStartedActivity
        assertEquals(DifficultyActivity::class.java.name, nextIntent.component?.className)
    }

}