package com.example.cse5236.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cse5236.model.Account
import com.example.cse5236.model.AccountRepository
import com.example.cse5236.model.AuthenticationRepository
import kotlinx.coroutines.runBlocking

class AuthenticationViewModel : ViewModel() {
    private val authRepo: AuthenticationRepository = AuthenticationRepository()
    private val _signInStatus = MutableLiveData<String>()
    val signInStatus: LiveData<String> = _signInStatus
    private val _signUpStatus = MutableLiveData<String>()
    val signUpStatus: LiveData<String> = _signUpStatus

    fun signUp(email: String, password: String) {
        Log.i("ViewModel", "AuthenticationViewModel signUp")
        authRepo.signUp(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    AccountRepository.setAccount(Account(email))
                    _signUpStatus.value = "Sign up successful."
                } else {
                    _signUpStatus.value = "Sign up error: ${task.exception?.message}"
                }
            }
    }

    fun signIn(email: String, password: String) {
        Log.i("ViewModel", "AuthenticationViewModel signIn")
        runBlocking {
            authRepo.signIn(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        AccountRepository.setAccount(Account(email))
                        _signInStatus.value = "Sign in successful."
                    } else {
                        _signInStatus.value = "Sign in error: ${task.exception?.message}"
                    }
                }
        }
    }

}