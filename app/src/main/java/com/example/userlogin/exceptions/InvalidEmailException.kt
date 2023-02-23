package com.example.userlogin.exceptions

interface InvalidEmailException {
    fun isValidEmail(email:String):Boolean
}