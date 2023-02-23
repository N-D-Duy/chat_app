package com.example.userlogin.adapter

import android.content.Context
import android.content.SharedPreferences
import com.example.userlogin.model.User
import com.google.gson.Gson


const val MY_SHARED_PREFERENCES: String = "UserPreferences"

class MySharedPreferences(context: Context) {
    private val mContext = context
    fun putBooleanValue(key: String, value: Boolean) {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanValue(key: String): Boolean {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(key, false)
    }

    fun putUserProfile(key: String, user: User) {
        val sharedPref = mContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        ) ?: return
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        editor.putString(key, json)
        editor.apply()
    }

    fun getUserProfile(key: String): User {
        val sharedPref = mContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = sharedPref.getString(key, null)
        return gson.fromJson(json, User::class.java)
    }

    fun removeByKey(key: String) {
        val sharedPref = mContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        ) ?: return
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }

    fun putStringValue(key: String, value: String) {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringValue(key: String): String? {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, null)
    }

}