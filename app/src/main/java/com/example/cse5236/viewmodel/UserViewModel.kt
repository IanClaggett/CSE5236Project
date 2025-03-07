package com.example.cse5236.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cse5236.model.AuthenticationRepository
import com.example.cse5236.model.Score
import com.example.cse5236.model.User
import com.example.cse5236.model.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class UserViewModel : ViewModel() {
    private val authRepo: AuthenticationRepository = AuthenticationRepository()
    private val userRepo: UserRepository = UserRepository()
    private val _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData

    fun signOut(){
        authRepo.signOut()
    }

    /*
    FOR ALL CRUD STUFF, .addOnSucessListener & .addOnFailureListener
     */

    //user limit of 5
    fun addUser(){

    }


    fun getUsers(){
        userRepo.getUsers().addOnSuccessListener { result ->
            val userList: List<User> = result.documents.map { user ->
                val userDifficulty: String = user.getString("difficulty") ?: "default"
                val userScores: List<Score>? = getUserScores(user.id)
                User(userDifficulty, userScores)
            }
            _usersLiveData.value = userList
        }.addOnFailureListener { exception ->
            Log.d("Firestore", "Error getting documents: ", exception)
        }
    }

    private fun getUserScores(userId: String): List<Score>? {
        val scoreList: MutableList<Score> = mutableListOf<Score>()
        userRepo.getUserScores(userId).addOnSuccessListener { result ->
            result.documents.map {scoreDocument ->
                val scorePoints: Number = scoreDocument.getString("score")?.toInt() ?: 0
                scoreList.add(Score(scorePoints))
            }
        }.addOnFailureListener { exception ->
            Log.d("Firestore", "Error getting score document for user: ", exception)
        }
        return scoreList
    }

    fun deleteUsers(){

    }

    fun updateUsers(){

    }


}