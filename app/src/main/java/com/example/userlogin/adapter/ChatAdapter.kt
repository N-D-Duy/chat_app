package com.example.userlogin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userlogin.R
import com.example.userlogin.model.ChatMessage
import kotlinx.android.synthetic.main.item_chat_received_message.view.*
import kotlinx.android.synthetic.main.item_chat_received_message.view.tv_chat_time
import kotlinx.android.synthetic.main.item_chat_sent_message.view.*

open class ChatAdapter(list:ArrayList<ChatMessage>, senderId:String, imgUrl:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var listMessages = list
    val mImgUrl = imgUrl
    val mSenderId = senderId
    val VIEW_TYPE_SEND = 1
    val VIEW_TYPE_RECEIVED = 2

    fun swapData(newList:ArrayList<ChatMessage>){
        listMessages = newList
        notifyDataSetChanged()
    }
    fun addMessage(position: Int){
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==1){
            SendViewHolder(itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_sent_message, parent, false))
        } else{
            ReceiveViewHolder(itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_received_message, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(listMessages[position].senderId == mSenderId){
            VIEW_TYPE_SEND
        } else{
            VIEW_TYPE_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        return listMessages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = listMessages[position]
        if (getItemViewType(position) == VIEW_TYPE_SEND) {
            (holder as SendViewHolder).bind(message)
        } else {
            (holder as ReceiveViewHolder).bind(message)
        }
    }

    inner class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mItemView = itemView
        fun bind(chatMessage: ChatMessage) {
            mItemView.apply {
                tv_chat_time.text = chatMessage.dateTime
                tv_content_sent_message.text = chatMessage.message
            }
        }
    }

    inner class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chatMessage: ChatMessage) {
            itemView.apply {
                tv_chat_time.text = chatMessage.dateTime
                tv_content_received_message.text = chatMessage.message
                ConvertImage(context).urlToImage(mImgUrl, img_chat_received_message)
            }
        }
    }

}