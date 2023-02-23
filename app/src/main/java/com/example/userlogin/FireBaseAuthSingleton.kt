package com.example.userlogin

import com.google.firebase.auth.FirebaseAuth


object FireBaseAuthSingleton {
        val instance = FirebaseAuth.getInstance()
}
