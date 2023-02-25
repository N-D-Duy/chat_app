package com.example.userlogin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.userlogin.FireBaseAuthSingleton
import com.example.userlogin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*

class LoadingCreateProfileActivity : AppCompatActivity() {

    private val dbRef = FirebaseDatabase.getInstance(
        "https://myapplication-43a36-default-rtdb.asia-southeast1.firebasedatabase.app"
    )
        .reference
    private val myScope = CoroutineScope(Dispatchers.Main + Job())
    private suspend fun getData(): String {
        val user = FireBaseAuthSingleton.instance.currentUser
        return if(user!= null){
            val uid = user.uid
            checkProfileIsCreated(uid)
            delay(1000)
            "Dữ liệu đã lấy được"
        }else "User null"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_create_profile)


        checkAndSwitchScreen()



    }


    private fun checkProfileIsCreated(uid: String){
        val checkRef = dbRef.child("users-profile").child(uid)
        checkRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    goToLoadingHomeActivity()
                } else{
                    goToCreateProfile()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun checkAndSwitchScreen() {
        myScope.launch{
            val result = getData()
            Log.w("coroutines get data", result)
        }
    }


    private fun goToLoadingHomeActivity() {
        val intent = Intent(this, LoadingHomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToCreateProfile() {
        val intent = Intent(this, CreateProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}