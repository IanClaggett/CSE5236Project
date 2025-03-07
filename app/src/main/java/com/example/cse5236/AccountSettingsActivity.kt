package com.example.cse5236

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import org.w3c.dom.Text

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
                val newData = arrayListOf(username, score)
                val updateMap = mapOf(email to newData)

                val fireStore = Firebase.firestore
                fireStore.collection("UserInformation").document(email).delete()
                fireStore.collection("UserInformation").document(email).set(updateMap).addOnSuccessListener {
                    System.out.println("SUCCESS")
                }.addOnFailureListener{
                    System.out.println("FAILURE")
                }
                Toast.makeText(this, "Updated username", Toast.LENGTH_SHORT)
            }
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
            val docRef = fireStore.collection("UserInformation").document(it.email.toString())
            docRef.get().addOnSuccessListener { document->
                if(document!=null){
                    lateinit var userData : List<Any>
                    userData = document.data?.get(it.email.toString()) as List<Any>
                    val username = userData[0]
                    val score = userData[1]
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