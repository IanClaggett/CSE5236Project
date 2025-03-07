package com.example.cse5236.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class UserRepository() {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("Users")

    fun getUsers(): Task<QuerySnapshot> {
        return users.get()
    }

    //make default impl of user, need difficulty and scores>autogenscoreID
    fun addUser(username: String): Task<DocumentReference>{
        return users.add("hi")
    }

    fun deleteUser(username: String): Task<Void> {
        return users.document(username).delete()
    }

    //need to impl, go thru all users, make combined list of scores in each user
    fun getAccountScores(): Task<QuerySnapshot>{
        return getUsers()
    }

    fun getUserScores(userId: String): Task<QuerySnapshot> {
        val userDoc = users.document(userId)
        return userDoc.collection("scores").get()
    }


/*
    USE THIS TEMPLATE FOR UPDATING A USER
    fun updateRENAMEME(username: String, map: Map<DataType, DataType>): Task<Void>{
        return users.document(username).update(map)
    }
 */

}