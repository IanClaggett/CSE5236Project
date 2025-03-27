package com.example.cse5236.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cse5236.model.Account
import com.example.cse5236.model.AccountRepository
import com.example.cse5236.model.AuthenticationRepository
import com.example.cse5236.model.Score
import com.example.cse5236.model.User
import com.example.cse5236.model.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.getField
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel : ViewModel() {
    private val authRepo: AuthenticationRepository = AuthenticationRepository()
    private val accRepo: AccountRepository = AccountRepository
    private val userRepo: UserRepository = UserRepository()
    private val _usersLiveData: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData
    private val _accountScoresLiveData: MutableLiveData<List<Pair<String, Score>>> = MutableLiveData<List<Pair<String, Score>>>()
    val accountScoresLiveData: LiveData<List<Pair<String, Score>>> = _accountScoresLiveData

    fun signOut(){
        Log.i("ViewModel", "UserViewModel signOut")
        authRepo.signOut()
    }

    fun addUser(username: String): String{

        Log.i("ViewModel", "UserViewModel addUser")
        var responseString: String = "Success!"
        //handle async function return early, need to use coroutines await, make a loading thing
        if (getUserAmount() < 5){
            userRepo.addUser(username, accRepo.getAccount()!!)
                .addOnSuccessListener {
//                    responseString = addScorePlaceholder(username) ?: responseString
                    //lazy update usersLiveData
                    getUsers()
                }
                .addOnFailureListener { e ->
                    responseString = "Failed to add user $username"
                    Log.e("Firestore", "Couldn't add user: ", e)
                }
        } else {
            responseString = "User limit reached."
        }
        return responseString
    }

//    private fun addScorePlaceholder(username: String): String?{
//        var response: String? = null
//        userRepo.addScorePlaceholder(username, accRepo.getAccount()!!)
//            .addOnFailureListener { e ->
//                response = "Failed to add score placeholder."
//                Log.e("Firestore", "Couldn't add scorePlaceholder for $username: ", e)
//            }
//        return response
//    }


    fun getUserAmount(): Int{
        Log.i("ViewModel", "UserViewModel getUserAmount")
        var ans: Int = -1
        runBlocking {
            userRepo.getUsers(accRepo.getAccount()!!)
                .addOnSuccessListener { users ->
                    ans = 0
                    for (user in users){
                        ans += 1
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error getting users: ", e)
                }
        }
        return ans
    }

    fun deleteUser(username: String): String{
        Log.i("ViewModel", "UserViewModel deleteUsers")
        var response: String = "Success!"
        userRepo.deleteUser(username, accRepo.getAccount()!!)
            .addOnSuccessListener {
                getUsers()
            }
            .addOnFailureListener { e ->
                response = e.toString()
                Log.d("Firestore", "Error deleting user $username under account ${accRepo.getAccount()?.email}: ", e)
            }
        return response
    }

    //make UI display loading screen until getUsers finishes
    fun getUsers(){
        Log.i("ViewModel", "UserViewModel getUsers")
        var account: Account? = null
        if (accRepo.getAccount() != null){
            account = accRepo.getAccount()
        } else {
            Log.e("ViewModel", "Account is null in getUsers()")
        }
        userRepo.getUsers(account!!)
            .addOnSuccessListener { result ->
                val userList: List<User> = result.documents.map { user ->
                    val userDifficulty: String = user.getString("difficulty") ?: "default"
                    val userScores: List<Score> = getUserScores(user.id)
                    User(user.id, userDifficulty, userScores)
                }
                _usersLiveData.value = userList
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "Error getting documents: ", exception)
            }
    }

    private fun getUserScores(username: String): List<Score> {
        Log.i("ViewModel", "UserViewModel getUserScores")
        val scoreList: MutableList<Score> = mutableListOf<Score>()
        userRepo.getUserScores(username, accRepo.getAccount()!!)
            .addOnSuccessListener { scoresCollection ->
                scoresCollection.documents.map { scoreDocument ->
                    val scorePoints: Int? = scoreDocument.getString("points")?.toInt()
                    val difficulty: String? = scoreDocument.getString("difficulty")
                    if (scorePoints != null && difficulty != null){
                        scoreList.add(Score(difficulty, scorePoints))
                    }
                }
            }.addOnFailureListener { exception ->
                Log.d("Firestore", "Error getting score document for $username: ", exception)
            }
        return scoreList
    }



    //UPDATE USERLIVEDATA
    //figure out data type of updates from View
//    fun updateUsers(username: String): String{
//
//    }

    fun getAccountScores(){
        Log.i("ViewModel", "UserViewModel getAccountScores")
        userRepo.getUsers(accRepo.getAccount()!!)
            .addOnSuccessListener { result ->
                val ans: MutableList<Pair<String, Score>> = mutableListOf<Pair<String, Score>>()
                result.documents.map { user ->
                    val username: String = user.id
                    getUserScores(username).map { score ->
                        ans.add(username to score)
                    }
                }
                _accountScoresLiveData.value = ans
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "Error getting users: ", exception)
            }
    }


}