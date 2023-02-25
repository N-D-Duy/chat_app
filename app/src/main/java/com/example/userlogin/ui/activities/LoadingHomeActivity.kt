package com.example.userlogin.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.userlogin.adapter.UserProfileViewModel
import com.example.userlogin.databinding.ActivityLoadingHomeBinding

class LoadingHomeActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLoadingHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoadingHomeBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        viewModel.users.observe(this){
            if(it.isNotEmpty()){
                goToMainActivity()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}