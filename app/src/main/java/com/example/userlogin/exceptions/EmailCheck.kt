package com.example.userlogin.exceptions

import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress

class EmailCheck : InvalidEmailException{
    override fun isValidEmail(email: String): Boolean {
        return try {
            val address = InternetAddress(email)
            address.address == email
        } catch (e: AddressException) {
            false
        }
    }
}