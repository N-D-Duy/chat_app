package com.example.userlogin.model

import java.util.*

data class ChatMessage(
    var senderId:String = "",
    var receiverId:String = "",
    var message:String = "",
    var dateTime:String = "",
    var date:Date = Date(0)
)