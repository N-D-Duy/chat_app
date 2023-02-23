package com.example.userlogin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.userlogin.FireBaseAuthSingleton
import com.example.userlogin.R
import com.example.userlogin.adapter.HashPassword
import com.example.userlogin.adapter.ProgressDialogFragment
import com.example.userlogin.databinding.FragmentSignUpEmailBinding
import com.example.userlogin.model.User
import com.example.userlogin.ui.activities.SignInActivity
import com.example.userlogin.ui.activities.SignUpActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_sign_up_email.*
import java.util.*
import kotlin.collections.HashMap


class SignUpEmailFragment : Fragment() {
    private lateinit var binding: FragmentSignUpEmailBinding
    private val mAuth = FireBaseAuthSingleton.instance
    private val TAG = SignUpActivity::class.java.name
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
        binding = FragmentSignUpEmailBinding.inflate(inflater, container, false)

        binding.btnSignUpEmailContinue.isEnabled = true


        binding.edtSignUpEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                checkEmailExistence(s.toString())
            }

        })


        val progressDialog: ProgressDialogFragment = ProgressDialogFragment.newInstance()


        binding.btnSignUpEmailContinue.setOnClickListener {
            val email = edt_signUp_email.text.toString()
            val password = edt_signUp_email_password.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()) {
                progressDialog.show(childFragmentManager, "progress")
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mAuth.currentUser!!.sendEmailVerification()
                                .addOnCompleteListener { task1 ->
                                    if (task1.isSuccessful) {
                                        Toast.makeText(
                                            requireContext(),
                                            "please check email for verification.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        checkEmailVerified(mAuth.currentUser, password, email)
                                        progressDialog.dismiss()
                                        goToLoginActivity()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            task.exception!!.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }
            } else if (email.isEmpty()) {
                Toast.makeText(requireContext(), "empty email", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "empty password", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        return binding.root
    }

    private fun goToLoginActivity() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        startActivity(intent)
    }

    private fun checkEmailVerified(userFirebase: FirebaseUser?, password: String, email: String) {
        if (userFirebase!!.isEmailVerified) {
            Log.e("create account", "email verify successfully")

            //đẩy dữ liệu người dùng lên database

            val hashPassword = HashPassword().hashPassword(password)
            val user: MutableMap<String, Any> = HashMap()
            user["uid"] = userFirebase.uid
            user["email"] = email
            user["password"] = hashPassword.toString()

            dbRef.child("users").child(userFirebase.uid).setValue(user)
                .addOnCompleteListener {
                    Log.e("push data sign up", "completed")
                }.addOnFailureListener {
                    Log.e("push data sign up", "failure")
                }
        } else {
            Log.e("create account", "failed")
        }
    }


    private fun checkEmailExistence(email: String) {
        val query = dbRef.child("users").orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    btn_signUp_email_continue.isEnabled = false
                    tv_email_already_used.visibility = View.VISIBLE
                } else {
                    btn_signUp_email_continue.isEnabled = true
                    tv_email_already_used.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "query failed")
            }
        })
    }
}