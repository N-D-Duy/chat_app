package com.example.userlogin.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.userlogin.R
import com.example.userlogin.adapter.MySharedPreferences
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {
    private val TAG = SplashActivity::class.java.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val backgroundExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        backgroundExecutor.schedule({

            nextActivity()
        }, 1, TimeUnit.SECONDS)
    }

    private fun nextActivity() {
        val user = FirebaseAuth.getInstance().currentUser
        val prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        if(user== null){
            Log.e(TAG, "user null")
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Log.e(TAG, "user is not null")

            val uid = user.uid
            prefs.edit().putString("uid", uid).apply()

            val intent = Intent(this, LoadingHomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}