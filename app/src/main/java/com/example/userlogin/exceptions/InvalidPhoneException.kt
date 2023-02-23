package com.example.userlogin.exceptions

interface InvalidPhoneException {
    fun isValidPhone(phone: String):Boolean
}