package com.example.userlogin.model

data class User(
    var uid:String = "",
    var displayName: String = "",
    var email: String? = "",
    var phoneNumber: String? = "",
    var password: String = "",
    var imgUrl:String = "",
    var isCreatedProfile:Boolean = false
)