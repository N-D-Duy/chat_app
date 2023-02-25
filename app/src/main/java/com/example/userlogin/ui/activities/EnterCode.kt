package com.example.userlogin.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.userlogin.FireBaseAuthSingleton
import com.example.userlogin.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_enter_code.*
import java.util.concurrent.TimeUnit

class EnterCode : AppCompatActivity() {
    private lateinit var phoneNumber: String
    private lateinit var verificationId: String
    private val auth = FireBaseAuthSingleton.instance
    private val TAG = EnterCode::class.java.name
    private var isButtonEnabled = false

    private lateinit var countDownTimer: CountDownTimer
    private var countDownInterval = 1000L // 1 second
    private var timeLeft = 60000L // 60 seconds

    private val dbRef = FirebaseDatabase.getInstance(
        "https://myapplication-43a36-default-rtdb.asia-southeast1.firebasedatabase.app"
    )
        .reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_code)
        getDataIntent()

        onDelayButton()


        btn_confirm_smsCode.setOnClickListener {
            val strSmsCode = edt_sms_code.text.toString()
            onClickSendOtp(strSmsCode)
        }


        btn_send_again.setOnClickListener {
            onClickSendOtpAgain()
            onDelayButton()
        }

        enter_code_back.setOnClickListener {
            finish()
        }




    }

    private fun onDelayButton() {
        tv_time_countdown.visibility = View.VISIBLE
        btn_send_again.isEnabled = false
        countDownTimer = object : CountDownTimer(timeLeft, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                val timeLeftInSeconds = timeLeft / 1000
                tv_time_countdown.text = timeLeftInSeconds.toString()
            }

            override fun onFinish() {
                tv_time_countdown.visibility = View.GONE
                isButtonEnabled = true
                btn_send_again.isEnabled = true
            }
        }.start()
        timeLeft = 60000L
    }

    private fun onClickSendOtp(strSmsCode: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, strSmsCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun getDataIntent() {
        phoneNumber = intent.getStringExtra("phone number")!!
        verificationId = intent.getStringExtra("Id")!!
    }

    private fun onClickSendOtpAgain() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(p0)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(applicationContext, "VerificationFailed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    verificationId = p0
                }

            })          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(TAG, "signInWithCredential:success")

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
        val intent = Intent(this, LoadingCreateProfileActivity::class.java)
        startActivity(intent)
        finish()
    }


}