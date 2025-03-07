package com.example.cse5236.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.example.cse5236.R
import com.example.cse5236.viewmodel.AuthenticationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class SignUpFragment : Fragment() {

    private val viewModel: AuthenticationViewModel = AuthenticationViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            (activity as? AuthenticationActivity)?.navigateToFragment(WelcomeFragment())
        }

        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val signUpButton = view.findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            viewModel.signUpStatus.observe(viewLifecycleOwner, Observer { status ->
                if (status.toString() == "Sign up successful.") {
                    Toast.makeText(requireContext(), "Sign up successful!", Toast.LENGTH_SHORT).show()
                    (activity as? AuthenticationActivity)?.onUserSignedIn()
                } else {
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            })

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.signUp(email, password)

        }
    }
}
