package com.example.cse5236.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.cse5236.R

class ManageUsersActivity: AppCompatActivity()  {
    //val prefs = getSharedPreferences("users", Context.MODE_PRIVATE)
    //val users = arrayOf(prefs.all.keys)
    val users = arrayOf("user1","User2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_user)

        val spinnerMenu = findViewById<Spinner>(R.id.Users_Saved)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, users)
        spinnerMenu.adapter = arrayAdapter

        spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                println(users.get(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}