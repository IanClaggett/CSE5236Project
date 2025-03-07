package com.example.cse5236.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cse5236.model.AuthenticationRepository
import com.example.cse5236.model.UserRepository

class UserViewModel : ViewModel() {
    private val authRepo: AuthenticationRepository = AuthenticationRepository()
    private val userRepo: UserRepository = UserRepository()

    fun signOut(){
        authRepo.signOut()
    }

    /*
    FOR ALL CRUD STUFF, .addOnSucessListener & .addOnFailureListener
     */

    //user limit of 5
    fun addUser(){

    }

    //return usernames under the account
    fun getUsers(){
        userRepo.getUsers()
    }

    fun deleteUsers(){

    }

    fun updateUsers(){

    }


}