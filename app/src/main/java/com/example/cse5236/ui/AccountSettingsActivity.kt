package com.example.cse5236.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AccountSettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_settings)

        updateAccountInformation()

        val updateUsernameBtn = findViewById<Button>(R.id.updateAccountBtn)
        val deleteAccount = findViewById<Button>(R.id.deleteAccountBtn)


        updateUsernameBtn.setOnClickListener{
            val username = findViewById<EditText>(R.id.accountUsernameTV).text.trim()
            if(username.isNullOrBlank() || username.isEmpty()){
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                val score = Integer.parseInt(findViewById<TextView>(R.id.accountScoreTV).text.toString())

                val email = findViewById<TextView>(R.id.accountEmailTV).text.toString().trim()
                val updateMap = mutableMapOf("Username" to username.toString(), "Score" to score)

                val fireStore = Firebase.firestore
                fireStore.collection("UserInformationDB").document(email).set(updateMap).addOnSuccessListener {
                    System.out.println("SUCCESS")
                }.addOnFailureListener{
                    System.out.println("FAILURE")
                }
                Toast.makeText(this, "Updated username", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this,UserSelectActivity::class.java))
            finish()
        }

        deleteAccount.setOnClickListener{
            val fireStore = Firebase.firestore
            val user = Firebase.auth.currentUser
            lateinit var userEmail: String
            user?.let {
                userEmail = it.email.toString()
            }
            fireStore.collection("UserInformationDB").document(userEmail).delete()
            val userDeletion = Firebase.auth.currentUser!!
            userDeletion.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"Account Deleted Successfully", Toast.LENGTH_SHORT).show()
                    }
                }

            val auth = FirebaseAuth.getInstance()
            auth.signOut()  // Logs the user out
            val intent = Intent(this, AuthenticationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Clears activity stack
            startActivity(intent)
        }
    }

    private fun updateAccountInformation(){
        val accountEmailTV = findViewById<TextView>(R.id.accountEmailTV)
        val accountScore = findViewById<TextView>(R.id.accountScoreTV)
        val accountUsername = findViewById<TextView>(R.id.accountUsernameTV)

        val user = Firebase.auth.currentUser

        user?.let {
            accountEmailTV.text = it.email
            val fireStore = Firebase.firestore
            val docRef = fireStore.collection("UserInformationDB").document(it.email.toString())
            docRef.get().addOnSuccessListener { document->
                if(document!=null){
                    lateinit var userData : Map<Any,Any>
                    userData = document.data as Map<Any, Any>
                    val username = userData.get("Username")
                    val score = userData.get("Score")
                    accountScore.text = score.toString()
                    accountUsername.text = username.toString()
                }else{
                    System.out.println("FAILURE")
                }
            }.addOnFailureListener{
                System.out.println("QUERY FAILED")
            }

        }



    }
}