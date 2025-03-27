package com.example.cse5236.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cse5236.R
import com.example.cse5236.viewmodel.UserViewModel
import android.content.Intent


class UserSelectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_select, container, false)
    }

    //display user strings from viewmodel.getUsers()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutBtn = view.findViewById<Button>(R.id.logoutBtn)
        val addUserBtn = view.findViewById<Button>(R.id.addUserBtn)
        val accountSettings =view.findViewById<Button>(R.id.accountSettings)
        val viewModel = UserViewModel()

        logoutBtn.setOnClickListener {
            viewModel.signOut()
            (activity as? UserSelectActivity)?.onUserLogout()
        }

        accountSettings.setOnClickListener{
            (activity as? UserSelectActivity)?.onUserSettings()
        }

        val startGameBtn = view.findViewById<Button>(R.id.btnStartGame)
        startGameBtn.setOnClickListener {
            val intent = Intent(requireContext(), DifficultyActivity::class.java)
            startActivity(intent)
        }

    }

}