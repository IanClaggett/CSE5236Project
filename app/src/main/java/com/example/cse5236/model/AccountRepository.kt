package com.example.cse5236.model

//necessary for updating auth email/password
object AccountRepository {
    private var accountData: Account? = null

    fun getAccount(): Account?{
        return accountData
    }

    fun setAccount(account: Account) {
        accountData = account
    }

    fun clearAccount(){
        accountData = null
    }



}
