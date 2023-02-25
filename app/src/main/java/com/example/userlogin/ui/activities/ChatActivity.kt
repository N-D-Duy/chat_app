package com.example.userlogin.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlogin.adapter.ChatAdapter
import com.example.userlogin.adapter.ConvertImage
import com.example.userlogin.databinding.ActivityChatBinding
import com.example.userlogin.model.ChatMessage
import com.example.userlogin.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private var chatMessages: ArrayList<ChatMessage> = arrayListOf()
    val mFireStore = FirebaseFirestore.getInstance()
    private val mUid = FirebaseAuth.getInstance().currentUser!!.uid
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var user: UserProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.getSerializableExtra("user-to-chat") as UserProfile
        binding.rcvChat.layoutManager = LinearLayoutManager(this)


        loadHeader(user)

        binding.imageChatBack.setOnClickListener {
            finish()
        }
        chatAdapter = ChatAdapter(list = chatMessages, senderId = mUid, imgUrl = user.imgUrl)
        binding.rcvChat.adapter = chatAdapter

        binding.layoutSend.setOnClickListener {
            sendMessage(user)
        }

        listenMessage()

    }

    private fun loadHeader(user: UserProfile) {
        ConvertImage(this).urlToImage(user.imgUrl, binding.imgChatTop)
        binding.tvChatTop.text = user.displayName
    }

    private fun sendMessage(user: UserProfile) {
        val message: MutableMap<String, Any> = HashMap()
        message["senderId"] = mUid
        message["receiverId"] = user.uid
        message["message"] = binding.inputMessage.text.toString()
        message["timestamp"] = Date()
        mFireStore.collection("message").add(message)
        binding.inputMessage.text = null
    }

    private fun getDateFromDatabase(date: Date): String {
        return SimpleDateFormat("MMMM, dd, yyyy - hh:mm a", Locale.getDefault()).format(date)
    }

    private fun listenMessage() {
        mFireStore.collection("message")
            .whereEqualTo("senderId", mUid)
            .whereEqualTo("receiverId", user.uid)
            .addSnapshotListener(eventListener)

        mFireStore.collection("message")
            .whereEqualTo("senderId", user.uid)
            .whereEqualTo("receiverId", mUid)
            .addSnapshotListener(eventListener)
    }

    private val eventListener = EventListener<QuerySnapshot> { value, error ->
        if (error != null) return@EventListener
        if (value != null) {
            val count = chatMessages.size
            for (documentChange: DocumentChange in value.documentChanges) {
                val chatMessage = ChatMessage()
                chatMessage.senderId = documentChange.document.getString("senderId")!!
                chatMessage.receiverId = documentChange.document.getString("receiverId")!!
                chatMessage.message = documentChange.document.getString("message")!!
                chatMessage.dateTime =
                    getDateFromDatabase(documentChange.document.getDate("timestamp")!!)
                chatMessage.date = documentChange.document.getDate("timestamp")!!
                chatMessages.add(chatMessage)
            }

            val dateComparator = compareBy<ChatMessage> { it.date }
            chatMessages = chatMessages.sortedWith(dateComparator).toCollection(ArrayList())

            if (count == 0) {
                chatAdapter.swapData(chatMessages)
            } else {
                chatAdapter.addMessage(chatMessages.size-1)
                binding.rcvChat.smoothScrollToPosition(chatMessages.size - 1)
            }
            binding.rcvChat.visibility = View.VISIBLE
        }
        binding.progressChat.visibility = View.GONE

    }
}