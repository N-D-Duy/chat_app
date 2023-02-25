package com.example.userlogin.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.userlogin.R
import com.example.userlogin.adapter.MySharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_profile.*
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException


const val PICK_IMAGE_REQUEST = 1
class CreateProfileActivity : AppCompatActivity() {
    private lateinit var mImageUri:Uri
    private lateinit var userNameProfile:String
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid
    private val mFireStore = FirebaseFirestore.getInstance()
    private val mStorageRef = FirebaseStorage.getInstance(
        "gs://myapplication-43a36.appspot.com")
        .reference

    private val dbRef = FirebaseDatabase.getInstance(
        "https://myapplication-43a36-default-rtdb.asia-southeast1.firebasedatabase.app"
    )
        .reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        btn_done_profile.visibility = View.GONE

        layout_image.setOnClickListener{
            openFileChooser()
        }
        edt_profile_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()){
                    btn_done_profile.visibility = View.GONE
                }else{
                    btn_done_profile.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        btn_done_profile.setOnClickListener {
            upLoadProfileToDB(uid, userNameProfile)
            goToMainActivity()
            updateProfileStatus()
        }
    }

    private fun updateProfileStatus() {
        val prefs = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("isCreatedProfile", true).apply()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //encode image
    private fun uploadBitmap(bitmap: Bitmap) {
        userNameProfile = edt_profile_name.text.toString()
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()
        val reference: StorageReference = mStorageRef.child("images/$userNameProfile.png")
        val uploadTask: UploadTask = reference.putBytes(data)
        uploadTask.addOnFailureListener{
            Log.e("task", it.message.toString())
        }.addOnSuccessListener {
            Log.e("task", "successfully")
        }
    }


    //decode image
    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            Picasso.get().load(mImageUri).into(image_profile)
            tv_add_image.visibility = View.GONE
            uploadBitmap(getBitmapFromUri(mImageUri)!!)
        }
    }

    private fun upLoadProfileToDB(uid:String, displayName: String){
        val storageReference = mStorageRef.child("images/$displayName.png")
        storageReference.downloadUrl.addOnSuccessListener { uri -> // Lấy URL hình ảnh
            val imageUrl = uri.toString()

            // Lưu thông tin người dùng vào realtime và thông báo profile đã được tạo
            val userProfile: MutableMap<String, Any> = HashMap()
            userProfile["displayName"] = displayName
            userProfile["imageUrl"] = imageUrl
            userProfile["isCreatedProfile"] = true
            dbRef.child("users-profile").child(uid).updateChildren(userProfile)

            // lưu thông tin profile người dùng lên FireStore

            val userProfileStore = hashMapOf(
                "displayName" to displayName,
                "imageUrl" to imageUrl,
                "uid" to uid
            )
            mFireStore.collection("users-profile").document(uid)
                .set(userProfileStore)
                .addOnSuccessListener {
                    Log.d("fire store", "DocumentSnapshot added")
                }
                .addOnFailureListener { e ->
                    Log.w("fire store", "Error adding document", e)
                }

        }
    }
}