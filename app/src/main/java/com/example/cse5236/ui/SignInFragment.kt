package com.example.cse5236.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.cse5236.R
import com.example.cse5236.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private val viewModel: AuthenticationViewModel = AuthenticationViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            (activity as? AuthenticationActivity)?.navigateToFragment(WelcomeFragment())
        }

        val emailEditText = view.findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = view.findViewById<EditText>(R.id.editTextPassword)
        val signInButton = view.findViewById<Button>(R.id.btnSignIn)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            viewModel.signInStatus.observe(viewLifecycleOwner, Observer { status ->
                if (status.toString() == "Sign in successful.") {
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                    (activity as? AuthenticationActivity)?.onUserSignedIn()
                } else {
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            })

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.signIn(email, password)

        }


    }
}
