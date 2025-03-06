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

        btnSignIn.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(SignIn())
        }

        btnSignUp.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(SignUp())
        }
    }
}
