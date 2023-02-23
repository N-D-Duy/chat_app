package com.example.userlogin.model

import android.R.attr
import com.google.firebase.database.FirebaseDatabase


class UpdateUser {
    private val dbRef = FirebaseDatabase.getInstance(
        "https://myapplication-43a36-default-rtdb.asia-southeast1.firebasedatabase.app"
    )
        .reference.child("users")
    fun updatePhoneNumber(uid:String, phoneNumber:String?){
        val updates: MutableMap<String, Any> = HashMap()
        if (phoneNumber != null) {
            updates["phoneNumber"] = phoneNumber
        }
        dbRef.child(uid).updateChildren(updates)
    }

    fun updateEmail(uid:String, email:String?){
        val updates: MutableMap<String, Any> = HashMap()
        if (email != null) {
            updates["email"] = email
        }
        dbRef.child(uid).updateChildren(updates)
    }

    fun updateDisplayName(uid:String, displayName:String?){
        val updates: MutableMap<String, Any> = HashMap()
        if (displayName != null) {
            updates["phoneNumber"] = displayName
        }
        dbRef.child(uid).updateChildren(updates)
    }

    fun updatePassword(uid:String, password:String?){
        val updates: MutableMap<String, Any> = HashMap()
        if (password != null) {
            updates["phoneNumber"] = password
        }
        dbRef.child(uid).updateChildren(updates)
    }

    fun updateImgUrl(uid:String, imgUrl:String?){
        val updates: MutableMap<String, Any> = HashMap()
        if (imgUrl != null) {
            updates["phoneNumber"] = imgUrl
        }
        dbRef.child(uid).updateChildren(updates)
    }

}