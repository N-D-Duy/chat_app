package com.example.userlogin.exceptions

class UserNameCheck: InvalidUserName {
    private val pattern = "^[a-zA-Z]{5,15}$"
    private val regex = Regex(pattern)

    override fun isValidUserName(s: String): Boolean {
        return regex.matches(s)
    }
}