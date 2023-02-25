package com.example.userlogin.adapter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userlogin.model.User
import com.example.userlogin.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserProfileViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _users = MutableLiveData<ArrayList<UserProfile>>()
    val users: LiveData<ArrayList<UserProfile>>
        get() = _users

    init {
        getData()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getData() {
        GlobalScope.launch(Dispatchers.Main) {
            val snapshot = db.collection("users-profile").get().await()
            val documents = snapshot.documents
            val users = ArrayList<UserProfile>()
            for (document in documents) {

                val user = UserProfile(
                    document.data!!["uid"].toString(),
                    document.data!!["displayName"].toString()
                    ,document.data!!["imageUrl"].toString()

                )
                Log.e("get data view model", "success")
                users.add(user)
            }
            _users.postValue(users)
        }
    }
}