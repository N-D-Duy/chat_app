package com.example.userlogin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.userlogin.ui.fragments.SignUpEmailFragment
import com.example.userlogin.ui.fragments.SignUpPhoneFragment


class SignUpViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> SignUpEmailFragment()
            1-> SignUpPhoneFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }


}