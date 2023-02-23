package com.example.userlogin.ui.activities


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.userlogin.R
import com.example.userlogin.adapter.SignUpViewPagerAdapter
import com.example.userlogin.databinding.ActivitySignUpBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : FragmentActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        val viewPager = findViewById<ViewPager2>(R.id.viewpager_signUp)
        val adapter = SignUpViewPagerAdapter(this)
        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator_signUp)
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)

        btn_sign_up_back.setOnClickListener {
            finish()
        }

    }

}