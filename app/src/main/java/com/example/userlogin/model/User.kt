package com.example.userlogin.model

import com.google.firebase.database.PropertyName

data class User(
//    @PropertyName("uid")
    var uid:String = "",

    @PropertyName("displayName")
    var displayName: String = "",

//    @PropertyName("email")
    var email: String? = "",

//    @PropertyName("phoneNumber")
    var phoneNumber: String? = "",

//    @PropertyName("password")
    var password: String = "",

    @PropertyName("imageUrl")
    var imgUrl:String = "",

    @PropertyName("isCreatedProfile")
    var isCreatedProfile:Boolean = false
)