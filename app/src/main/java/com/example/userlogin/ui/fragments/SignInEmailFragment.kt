package com.example.userlogin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.userlogin.FireBaseAuthSingleton
import com.example.userlogin.adapter.ProgressDialogFragment
import com.example.userlogin.databinding.FragmentSignInEmailBinding
import com.example.userlogin.ui.activities.LoadingCreateProfileActivity


class SignInEmailFragment : Fragment() {
    private lateinit var binding: FragmentSignInEmailBinding
    private val progressDialog: ProgressDialogFragment = ProgressDialogFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInEmailBinding.inflate(inflater, container, false)
        binding.btnSignInEmailContinue.isEnabled = false


        //khi edittext trống, tắt button, khi người dùng focus edittext, tắt textview
        binding.edtSignInEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTexts()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.edtSignInEmailPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTexts()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        // Set up the EditText focus change listener to hide the error message
        binding.edtSignInEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.tvInvalidSignInEmail.visibility = View.GONE
            }
        }

        binding.edtSignInEmailPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.tvInvalidSignInEmail.visibility = View.GONE
            }
        }



        binding.btnSignInEmailContinue.setOnClickListener {
            clickContinue()
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun clickContinue() {
        progressDialog.show(childFragmentManager, "progress")
        val email = binding.edtSignInEmail.text.toString().trim()
        val password = binding.edtSignInEmailPassword.text.toString().trim()

        checkSignIn(email, password)
    }

    private fun checkEditTexts() {
        // Enable the Button if both EditTexts are not empty
        val email = binding.edtSignInEmail.text.toString().trim()
        val password = binding.edtSignInEmailPassword.text.toString().trim()
        binding.btnSignInEmailContinue.isEnabled = email.isNotEmpty() && password.isNotEmpty()
    }

    private fun checkSignIn(email: String, password: String) {
        val mAuth = FireBaseAuthSingleton.instance
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){

                Log.w("sign in", "successful")
                goToLoadingActivity()

            }else{
                progressDialog.dismiss()
                Log.w("sign in error", it.exception?.message.toString())
                binding.tvInvalidSignInEmail.visibility = View.VISIBLE
            }
        }
    }

    private fun goToLoadingActivity() {
        val intent = Intent(requireContext(), LoadingCreateProfileActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}