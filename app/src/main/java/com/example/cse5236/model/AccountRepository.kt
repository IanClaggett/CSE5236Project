package com.example.cse5236.model

import android.util.Log

//necessary for updating auth email/password
object AccountRepository {
    private var accountData: Account? = null

    fun getAccount(): Account?{
        Log.i("AccountSingleton", "AccountRepository getAccount")
        return accountData
    }

    fun setAccount(account: Account) {
        Log.i("AccountSingleton", "AccountRepository setAccount")
        accountData = account
    }

    fun clearAccount(){
        Log.i("AccountSingleton", "AccountRepository clearAccount")
        accountData = null
    }



}
