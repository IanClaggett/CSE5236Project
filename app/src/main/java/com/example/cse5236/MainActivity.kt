package com.example.cse5236

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

//    private val firebaseAuth = FirebaseAuth.getInstance()
//
//    fun isLoggedIn(): Boolean {
//        if (firebaseAuth.currentUser != null) {
//            println("Already logged in!")
//            return true
//        }
//
//        return false
//    }
//
//    suspend fun register( email: String, password: String): Boolean {
//        try {
//            val result = suspendCoroutine { continuation ->
//
//                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
//                    println("registered account, success!")
//                }
//                    .addOnFailureListener {
//                        println("registration failure")
//                        continuation.resume(false)
//                    }
//            }
//
//            return result
//        } catch (e: Exception) {
//            e.printStackTrace()
//            if (e is CancellationException) throw e
//            println("Register exception ${e.message}")
//            return false
//        }
//
//    }
//
//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val signUp = SignUp()
        val signIn = SignIn()

        val btnSignIn = findViewById<View>(R.id.btnSignIn)
        val btnSignUp = findViewById<View>(R.id.btnSignUp)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FrameFragmentView, signUp)
            commit()
        }

        btnSignIn.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.FrameFragmentView, signIn)
                commit()
            }
        }

        btnSignUp.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.FrameFragmentView, signUp)
                commit()
            }
        }
    }


}