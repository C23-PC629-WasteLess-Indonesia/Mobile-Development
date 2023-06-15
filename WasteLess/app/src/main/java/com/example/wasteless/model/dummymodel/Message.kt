package com.example.wasteless.model.dummymodel


data class Message(
    val messageId: String? = "",
    val foodId: Int? = -1,
    val senderId: Int? = -1,
    val namaSender: String? = "",
    val receiverId: Int? = -1,
    val namaReceiver: String? = "",
)

data class Conversation(
    val messageId: String? = "",
    val senderId: Int? = -1,
    val message: String? = "",
    val time: Long? = 0
)






