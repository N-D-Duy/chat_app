package com.example.userlogin.exceptions

class PhoneCheck : InvalidPhoneException{
    override fun isValidPhone(phone: String): Boolean {
        val phoneRegex = "^[+]?[0-9]{10,13}\$"
        return phone.matches(phoneRegex.toRegex())
    }
}