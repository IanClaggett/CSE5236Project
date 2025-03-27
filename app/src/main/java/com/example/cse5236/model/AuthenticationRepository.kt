package com.example.cse5236.model

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository() {

    private val auth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String): Task<AuthResult> {
        Log.i("Repo", "AuthenticationRepository signIn")
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        Log.i("Repo", "AuthenticationRepository signUp")
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signOut() {
        Log.i("Repo", "AuthenticationRepository signOut")
        auth.signOut()
        AccountRepository.clearAccount()
    }
}
