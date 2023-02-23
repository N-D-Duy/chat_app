/*
package com.example.userlogin.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.userlogin.ui.fragments.SignUpEmailFragment
import com.example.userlogin.ui.fragments.SignUpPhoneFragment
import kotlinx.android.synthetic.main.activity_sign_up.*


class CustomDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        // Set any required click listeners or logic here
        return  inflater.inflate(com.example.userlogin.R.layout.activity_sign_up
            , container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog!!.window
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signup_email.setOnClickListener {
            val intent = Intent(requireContext().applicationContext,  SignUpEmailFragment::class.java)
            startActivity(intent)
        }

        signup_phone.setOnClickListener {
            val intent = Intent(requireContext().applicationContext,  SignUpPhoneFragment::class.java)
            startActivity(intent)
        }
    }

    fun show(fragmentManager: FragmentManager?) {
        if (fragmentManager != null) {
            super.show(fragmentManager, "CustomDialog")
        }
    }
}
*/
