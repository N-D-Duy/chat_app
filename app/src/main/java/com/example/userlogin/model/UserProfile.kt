package com.example.userlogin.model

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class UserProfile(
    var uid: String = "",

    @PropertyName("displayName")
    var displayName: String = "",

    @PropertyName("imageUrl")
    var imgUrl: String = ""

) : Serializable