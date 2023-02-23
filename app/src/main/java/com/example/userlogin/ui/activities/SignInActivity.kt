package com.example.userlogin.ui.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.userlogin.R
import com.example.userlogin.adapter.SignInViewPagerAdapter
import com.example.userlogin.databinding.ActivitySignInBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlin.properties.Delegates

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val sharedPref by lazy { getSharedPreferences("my_pref", Context.MODE_PRIVATE) }

    fun getMySharedPreferences(): SharedPreferences {
        return sharedPref
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        val viewPager = findViewById<ViewPager2>(R.id.viewpager_signIn)
        val adapter = SignInViewPagerAdapter(this)
        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator_signIn)
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)


        btn_sign_in_back.setOnClickListener {
            finish()
        }
    }
}