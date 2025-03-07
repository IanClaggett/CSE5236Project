package com.example.cse5236.model

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

//refactor everything under
    fun getEmail(): String {
        return accountData?.email ?: "No email found"
    }

//    fun updatePassword() {
//
//    }

    //return list, null case
    fun getUsers() {

    }

    fun addUser() {

    }

    //take in user
    fun updateUser() {

    }

    fun deleteUser() {

    }

//    //return list, need null case
//    fun getScores() {
//
//    }

}
