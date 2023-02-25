package com.example.userlogin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.userlogin.FireBaseAuthSingleton
import com.example.userlogin.databinding.FragmentSignInPhoneBinding
import com.example.userlogin.ui.activities.EnterCode
import com.example.userlogin.ui.activities.LoadingCreateProfileActivity
import com.example.userlogin.ui.activities.SignUpActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class SignInPhoneFragment : Fragment() {
    private lateinit var binding: FragmentSignInPhoneBinding
    private val TAG = SignUpActivity::class.java.name
    private val auth = FireBaseAuthSingleton.instance
    private lateinit var phoneNumber: String
    private val dbRef = FirebaseDatabase.getInstance(
        "https://myapplication-43a36-default-rtdb.asia-southeast1.firebasedatabase.app"
    )
        .reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInPhoneBinding.inflate(inflater, container, false)
        binding.btnSignInPhoneContinue.setOnClickListener {
            val strPhone = binding.edtSignInPhone.text.toString()
            phoneNumber = "+84" + strPhone.substring(1)
            onClickVerifyPhoneNumber(phoneNumber)
        }
        return binding.root
    }

    private fun onClickVerifyPhoneNumber(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(p0)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(
                        requireActivity().baseContext,
                        "VerificationFailed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    goToEnterCodeActivity(phoneNumber, p0)
                }

            })          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun goToEnterCodeActivity(phoneNumber: String, p0: String) {
        val intent = Intent(requireContext(), EnterCode::class.java)
        intent.putExtra("phone number", phoneNumber)
        intent.putExtra("Id", p0)
        startActivity(intent)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    val uid = user!!.uid
                    val updates: MutableMap<String, Any> = HashMap()

                    updates["uid"] = uid
                    dbRef.child("users").child(uid).updateChildren(updates)

                    goToLoadingActivity()

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.e(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun goToLoadingActivity() {
        val intent = Intent(requireContext(), LoadingCreateProfileActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}