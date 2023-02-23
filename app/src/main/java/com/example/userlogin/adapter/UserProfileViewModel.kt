package com.example.userlogin.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userlogin.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserProfileViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _users = MutableLiveData<ArrayList<User>>()
    val users: LiveData<ArrayList<User>>
        get() = _users

    init {
        getData()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getData() {
        GlobalScope.launch(Dispatchers.Main) {
            val snapshot = db.collection("users").get().await()
            val documents = snapshot.documents
            val users = ArrayList<User>()
            for (document in documents) {

                val user = User(
                    document.data!!["uid"].toString(),
                    document.data!!["username"].toString(),
                    " ",
                    " ",
                    " ",
                    document.data!!["imageUrl"].toString()
                )
                /*Log.e(
                    "get userprofile success",
                    document.id + "\n" + user.usernameProfile.javaClass + "\n" + user.imageUrl.javaClass
                )*/
                users.add(user)
            }
            _users.postValue(users)
        }
    }
}