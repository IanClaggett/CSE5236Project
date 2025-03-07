package com.example.cse5236

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

class SignUp : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(WelcomeFragment())
        }

        auth = FirebaseAuth.getInstance()

        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val signUpButton = view.findViewById<Button>(R.id.signUpButton)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        signUpButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        //Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()

                        val initialValues = arrayListOf("DefaultUsername", 0)
                        val initMap = mapOf(email to initialValues)

                        val fireStore = Firebase.firestore

                        fireStore.collection("UserInformation").document(email).set(initMap).addOnSuccessListener {
                            Toast.makeText(requireContext(), "User Data Initiliazed Successfully", Toast.LENGTH_LONG).show()
                            System.out.println("SUCCESS")
                        }.addOnFailureListener{
                            Toast.makeText(requireContext(), "User Data Failed Initialization", Toast.LENGTH_LONG).show()
                            System.out.println("FAILURE")
                        }

                        (activity as? MainActivity)?.onUserSignedIn()
                    } else {
                        Toast.makeText(requireContext(), "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
