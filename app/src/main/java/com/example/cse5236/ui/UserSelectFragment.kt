package com.example.cse5236.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.cse5236.R
import com.example.cse5236.model.User
import com.example.cse5236.viewmodel.UserViewModel
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class UserSelectFragment : Fragment() {

    private var viewModel: UserViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //add a delete mode button
        /*
            when clicked, click a user layout to delete it
            username is stored in layout's tag
         */
        val logoutBtn = view.findViewById<Button>(R.id.logoutBtn)
        val addUserBtn = view.findViewById<Button>(R.id.addUserBtn)
        val settingBtn = view.findViewById<Button>(R.id.btnSettings)
        //val acctManagementBtn = view.findViewById<Button>(R.id.btnAcctManagement)
        val viewModel = UserViewModel()
        val accountSettings = view.findViewById<Button>(R.id.accountSettings)
        viewModel.getUsers()
        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer { users ->
            loadUsers(users, view)
        })
        logoutBtn.setOnClickListener {
            viewModel.signOut()
            (activity as? UserSelectActivity)?.onUserLogout()
        }
        accountSettings.setOnClickListener{
            (activity as? UserSelectActivity)?.onUserSettings()
        }
        addUserBtn.setOnClickListener{
            Log.i("View", "UserSelectFragment onViewCreated")
            (activity as? UserSelectActivity)?.onAddAccount()
        }

        /*acctManagementBtn.setOnClickListener{
            (activity as? UserSelectActivity)?.onManageAccount()
        }
        */
        settingBtn.setOnClickListener{
            (activity as? UserSelectActivity)?.onSettings()
        }
        val startGameBtn = view.findViewById<Button>(R.id.btnStartGame)
        startGameBtn.setOnClickListener {
            val intent = Intent(requireContext(), DifficultyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUsers(users: List<User>, view: View){
        val size: Int = users.size
        val parentLayout =
            view.findViewById<GridLayout>(R.id.usersContainer)
        parentLayout.removeAllViews()
        if (size > 0){
            parentLayout.columnCount = size
            for (user in users){
                addUserElementToView(user)
            }
        } else {
            parentLayout.columnCount = 1
            val textView = getNoUsersDisplay()
            parentLayout.addView(textView)
        }
    }

    private fun getNoUsersDisplay(): TextView {
        val textView = TextView(requireContext())
        textView.text = "Please add a user"
        textView.textSize = 16f
        textView.gravity = Gravity.CENTER
        val params = GridLayout.LayoutParams()
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED)
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED)
        textView.layoutParams = params
        return textView
    }

    private fun addUserElementToView(user: User){
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER

        val imageView = ImageView(requireContext())
        imageView.setImageResource(R.drawable.default_profile_picture)


        val textView = TextView(requireContext())
        textView.text = user.username
        textView.textSize = 16f
        textView.gravity = Gravity.CENTER

        layout.addView(imageView)
        layout.addView(textView)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.tag = user.username
        layout.layoutParams = layoutParams
        layout.setOnClickListener{
            (activity as UserSelectActivity).onUserSelect(user)
            //go to userhomefragment
        }

        val parentLayout =
            requireView().findViewById<GridLayout>(R.id.usersContainer)
        parentLayout.addView(layout)
    }



}