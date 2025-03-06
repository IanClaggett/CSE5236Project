package com.example.cse5236

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSignIn = view.findViewById<Button>(R.id.btnSignIn)
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUp)

        /*
        May need to anonymize activity for SignIn/SignUp OR somehow load in MainActivity
        when SignIn/SignUp fragments needed
         */
        btnSignIn.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(SignInFragment())
        }

        btnSignUp.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(SignUpFragment())
        }
    }
}
