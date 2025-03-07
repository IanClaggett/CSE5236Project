package com.example.cse5236.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository() {

    private val auth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    AccountRepository.setAccount(Account(email, password))
                }
            }
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signOut() {
        auth.signOut()
        AccountRepository.clearAccount()
    }
}
